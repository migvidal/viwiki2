package com.migvidal.viwiki2.data

import android.util.Log
import com.migvidal.viwiki2.adapters.toDatabaseModel
import com.migvidal.viwiki2.adapters.toUiArticle
import com.migvidal.viwiki2.adapters.toUiDayImage
import com.migvidal.viwiki2.adapters.toUiFeaturedArticle
import com.migvidal.viwiki2.data.database.ViWikiDatabaseSpec
import com.migvidal.viwiki2.data.database.entities.DatabaseDayImage
import com.migvidal.viwiki2.data.database.entities.DatabaseDescription
import com.migvidal.viwiki2.data.database.entities.DatabaseImage
import com.migvidal.viwiki2.data.network.day.NetworkDayImage
import com.migvidal.viwiki2.data.network.day.NetworkFeaturedArticle
import com.migvidal.viwiki2.data.network.day.NetworkMostRead
import com.migvidal.viwiki2.data.network.day.WikiMediaApiImpl
import com.migvidal.viwiki2.ui.UiDayData
import com.migvidal.viwiki2.ui.UiDayImage
import com.migvidal.viwiki2.ui.UiFeaturedArticle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import java.util.GregorianCalendar

class DayRepository(private val viWikiDatabase: ViWikiDatabaseSpec) {

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
            val thumbnail = viWikiDatabase.imageDao.getImageById(image.thumbnailId)
            val fullSize = viWikiDatabase.imageDao.getImageById(image.imageId)
            val description = viWikiDatabase.descriptionDao.getDescriptionById(image.descriptionId)
            image.toUiDayImage(
                thumbnail = thumbnail ?: return@run null,
                fullSizeImage = fullSize ?: return@run null,
                description = description ?: return@run null,
            )
        }
        // Featured article
        val uiFeaturedArticle: UiFeaturedArticle? = featuredArticle?.let {
            val thumbnail = viWikiDatabase.imageDao.getImageById(it.thumbnailId ?: return@let null)
            val fullSize =
                viWikiDatabase.imageDao.getImageById(it.originalImageId ?: return@let null)
            featuredArticle.toUiFeaturedArticle(
                thumbnail = thumbnail ?: return@let null,
                fullSizeImage = fullSize ?: return@let null,
            )
        }
        // Finally, create the UiDay object
        UiDayData(
            featuredArticle = uiFeaturedArticle,
            mostReadArticles = mostRead.map { databaseArticle ->
                val thumbnail = databaseArticle.thumbnailId?.let {
                    viWikiDatabase.imageDao.getImageById(it)
                }
                databaseArticle.toUiArticle(thumbnail = thumbnail)
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
            originalImageId = insertedImageId, thumbnailId = insertedThumbnailId
        )
        viWikiDatabase.featuredArticleDao.insert(featuredArticle = dbFeaturedArticle)
    }

    private suspend fun cacheMostRead(mostRead: NetworkMostRead) {
        val databaseMostRead = mostRead.articles.map { networkArticle ->
            val thumbnail = networkArticle.thumbnail?.let {
                // - insert article thumbnail
                DatabaseImage(
                    source = it.source, width = it.width, height = it.height
                )
            }
            val originalImage = networkArticle.originalImage?.let {
                // - insert article image
                DatabaseImage(
                    source = it.source, width = it.width, height = it.height
                )
            }
            val insertedThumbnailId = thumbnail?.let {
                viWikiDatabase.imageDao.insert(it)
            }
            val insertedOriginalImageId = originalImage?.let {
                viWikiDatabase.imageDao.insert(it)
            }
            // - create Article object
            return@map networkArticle.toDatabaseModel(
                thumbnailId = insertedThumbnailId,
                originalImageId = insertedOriginalImageId,
                isOnThisDay = false,
                isMostRead = true,
                isFeatured = false,
            )
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