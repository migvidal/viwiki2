package com.migvidal.viwiki2.data

import android.util.Log
import com.migvidal.viwiki2.data.database.ViWikiDatabaseSpec
import com.migvidal.viwiki2.data.database.entities.DatabaseDayImage
import com.migvidal.viwiki2.data.database.entities.DatabaseDescription
import com.migvidal.viwiki2.data.database.entities.DatabaseImage
import com.migvidal.viwiki2.data.database.toDatabaseModel
import com.migvidal.viwiki2.data.network.NetworkDayImage
import com.migvidal.viwiki2.data.network.NetworkFeaturedArticle
import com.migvidal.viwiki2.data.network.NetworkMostRead
import com.migvidal.viwiki2.data.network.WikiMediaApiImpl
import com.migvidal.viwiki2.ui.UiArticle
import com.migvidal.viwiki2.ui.UiDayData
import com.migvidal.viwiki2.ui.UiDayImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import java.util.GregorianCalendar

class Repository(private val viWikiDatabase: ViWikiDatabaseSpec) {

    enum class DayDataStatus {
        Error, Success, Loading
    }

    private val _dayDataStatus = MutableStateFlow(DayDataStatus.Loading)
    val dayDataStatus = _dayDataStatus.asStateFlow()

    /**
     * Single source of truth for the "today" response
     */
    val dayData: Flow<UiDayData> = combine(
        flow = viWikiDatabase.featuredArticleDao.getAll(),
        flow2 = viWikiDatabase.mostReadArticlesDao.getMostRead(),
        flow3 = viWikiDatabase.dayImageDao.getAll(),
        flow4 = viWikiDatabase.onThisDayDao.getAll(),
    ) { featuredArticle, mostRead, image, onThisDay ->
        // Image of the day
        val uiDayImage: UiDayImage? = run {
            image ?: return@run null
            val thumbnail =
                viWikiDatabase.imageDao.getImageById(image.thumbnailId)
            val fullSize =
                viWikiDatabase.imageDao.getImageById(image.imageId)
            val description =
                viWikiDatabase.descriptionDao.getDescriptionById(image.descriptionId)
            UiDayImage.fromDatabaseEntity(
                databaseDayImage = image,
                thumbnail = thumbnail ?: return@run null,
                fullSizeImage = fullSize ?: return@run null,
                description = description ?: return@run null,
            )
        }
        // Finally, create the UiDay object
        UiDayData(
            databaseFeaturedArticle = featuredArticle,
            mostReadArticles = mostRead.map {
                val thumbnail = viWikiDatabase.imageDao.getImageById(it.thumbnailId)
                UiArticle.fromDatabaseEntity(databaseArticle = it, thumbnail = thumbnail)
            },
            image = uiDayImage,
            databaseOnThisDay = onThisDay,
        )
    }

    suspend fun refreshDayData() {
        val calendar = GregorianCalendar.getInstance()
        val year = calendar.get(GregorianCalendar.YEAR)
        val month = calendar.get(GregorianCalendar.MONTH)
        val day = calendar.get(GregorianCalendar.DAY_OF_MONTH)

        // Get from the network
        val networkDayResponse = runCatching {
            return@runCatching WikiMediaApiImpl.wikiMediaApiService.getFeatured(
                yyyy = year.toString(),
                mm = String.format("%02d", month),
                dd = String.format("%02d", day),
            )
        }.getOrElse {
            if (it is UnknownHostException) {
                Log.e("OkHttp error", it.message.toString())
            }
            it.printStackTrace()
            _dayDataStatus.update { DayDataStatus.Error }
            return
        }

        // Insert in DB
        withContext(context = Dispatchers.IO) {
            // Insert "featured article"
            val featuredArticle = networkDayResponse.featuredArticle
            if (featuredArticle == null) {
                _dayDataStatus.update { DayDataStatus.Error }
            } else {
                cacheFeaturedArticle(featuredArticle)
            }

            // Insert "image of the day"
            val dayImage = networkDayResponse.image
            if (dayImage == null) {
                _dayDataStatus.update { DayDataStatus.Error }
            } else {
                cacheDayImage(dayImage)
            }

            // Insert "most read articles"
            val mostRead = networkDayResponse.mostRead
            if (mostRead == null) {
                _dayDataStatus.update { DayDataStatus.Error }
            } else {
                cacheMostRead(mostRead)
            }
        }
        _dayDataStatus.update { DayDataStatus.Success }
        return
    }

    private suspend fun cacheFeaturedArticle(featuredArticle: NetworkFeaturedArticle) {
        // - insert full img and thumbnail
        val image = featuredArticle.originalImage
        val insertedImageId =
            viWikiDatabase.imageDao.insert(databaseImage = image.toDatabaseModel())
        val thumbnail = featuredArticle.thumbnail
        val insertedThumbnailId =
            viWikiDatabase.imageDao.insert(databaseImage = thumbnail.toDatabaseModel())

        // - insert the featured article
        val dbFeaturedArticle = featuredArticle.toDatabaseModel(
            originalImageId = insertedImageId,
            thumbnailId = insertedThumbnailId
        )
        viWikiDatabase.featuredArticleDao.insert(featuredArticle = dbFeaturedArticle)
    }

    private suspend fun cacheMostRead(mostRead: NetworkMostRead) {
        val databaseMostRead = mostRead.articles.map {
            // - insert article thumbnail
            val thumbnail = DatabaseImage(
                source = it.thumbnail.source,
                width = it.thumbnail.width,
                height = it.thumbnail.height
            )
            val insertedThumbnailId = viWikiDatabase.imageDao.insert(thumbnail)
            // - create Article object
            return@map it.toDatabaseModel(thumbnailId = insertedThumbnailId, isMostRead = true, isOnThisDay = false)
        }
        // - insert mostReadArticles
        viWikiDatabase.mostReadArticlesDao.insertAll(*databaseMostRead.toTypedArray())
    }

    private suspend fun cacheDayImage(dayImage: NetworkDayImage) = with(dayImage) {
        //- insert description
        val dbDescription = DatabaseDescription(
            text = description.text,
            lang = description.lang,
        )
        val insertedDescriptionId = viWikiDatabase.descriptionDao.insert(dbDescription)
        //- insert thumbnail
        val dbThumbnail = DatabaseImage(
            source = thumbnail.source,
            width = thumbnail.width,
            height = thumbnail.height,
        )
        val insertedThumbnailId = viWikiDatabase.imageDao.insert(dbThumbnail)
        //- insert full size image
        val fullSize = DatabaseImage(
            source = image.source,
            width = image.width,
            height = image.height,
        )
        val insertedFullSizeId = viWikiDatabase.imageDao.insert(fullSize)

        // insert DayImage
        val dbDayImage = DatabaseDayImage(
            thumbnailId = insertedThumbnailId,
            imageId = insertedFullSizeId,
            descriptionId = insertedDescriptionId,
            title = title,
        )
        viWikiDatabase.dayImageDao.insert(dbDayImage)
    }
}