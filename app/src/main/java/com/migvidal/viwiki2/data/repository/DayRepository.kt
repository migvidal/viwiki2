package com.migvidal.viwiki2.data.repository

import android.util.Log
import com.migvidal.viwiki2.adapters.toDatabaseModel
import com.migvidal.viwiki2.adapters.toUiArticle
import com.migvidal.viwiki2.adapters.toUiDayImage
import com.migvidal.viwiki2.adapters.toUiFeaturedArticle
import com.migvidal.viwiki2.data.database.ViWikiDatabaseSpec
import com.migvidal.viwiki2.data.database.entities.DatabaseArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseDayImage
import com.migvidal.viwiki2.data.database.entities.DatabaseDescription
import com.migvidal.viwiki2.data.database.entities.DatabaseImage
import com.migvidal.viwiki2.data.network.day.NetworkDayImage
import com.migvidal.viwiki2.data.network.day.NetworkFeaturedArticle
import com.migvidal.viwiki2.data.network.day.NetworkMostRead
import com.migvidal.viwiki2.data.network.day.WikiMediaApiImpl
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

class DayRepository(private val viWikiDatabase: ViWikiDatabaseSpec) : Repository {

    private val _dataStatus = MutableStateFlow(Repository.Status.Loading)
    override val dataStatus = _dataStatus.asStateFlow()

    /**
     * Single source of truth for the "today" response
     */
    override val data: Flow<UiDayData> = combine(
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
            val description =
                viWikiDatabase.descriptionDao.getDescriptionById(image.descriptionId)
            image.toUiDayImage(
                thumbnail = thumbnail ?: return@run null,
                fullSizeImage = fullSize ?: return@run null,
                description = description ?: return@run null,
            )
        }
        // Featured article
        val uiFeaturedArticle = featuredArticle?.let {
            val thumbnail =
                viWikiDatabase.imageDao.getImageById(it.thumbnailId)
            val fullSize =
                viWikiDatabase.imageDao.getImageById(it.originalImageId)
            featuredArticle.toUiFeaturedArticle(
                thumbnail = thumbnail ?: return@let null,
                fullSizeImage = fullSize ?: return@let null,
            )
        }
        // Finally, create the UiDay object
        UiDayData(
            featuredArticle = uiFeaturedArticle,
            mostReadArticles = mostRead.map { databaseArticle ->
                val thumbnail = viWikiDatabase.imageDao.getImageById(databaseArticle.thumbnailId)
                databaseArticle.toUiArticle(thumbnail = thumbnail)
            },
            image = uiDayImage,
            databaseOnThisDay = onThisDay,
        )
    }

    suspend fun refreshData() {
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
            _dataStatus.update { Repository.Status.Error }
            return
        }

        // Insert in DB
        withContext(context = Dispatchers.IO) {
            // Insert "featured article"
            val featuredArticle = networkDayResponse.featuredArticle
            if (featuredArticle == null) {
                _dataStatus.update { Repository.Status.Error }
            } else {
                cacheFeaturedArticle(featuredArticle)
            }

            // Insert "image of the day"
            val dayImage = networkDayResponse.image
            if (dayImage == null) {
                _dataStatus.update { Repository.Status.Error }
            } else {
                cacheDayImage(dayImage)
            }

            // Insert "most read articles"
            val mostRead = networkDayResponse.mostRead
            if (mostRead == null) {
                _dataStatus.update { Repository.Status.Error }
            } else {
                cacheMostRead(mostRead)
            }
        }
        _dataStatus.update { Repository.Status.Success }
        return
    }

    private suspend fun cacheFeaturedArticle(featuredArticle: NetworkFeaturedArticle) {
        // - insert full img and original
        val databaseImage = featuredArticle.originalImage.toDatabaseModel()
        viWikiDatabase.imageDao.insert(databaseImage = databaseImage)
        val thumbnail = featuredArticle.thumbnail
        viWikiDatabase.imageDao.insert(databaseImage = thumbnail.toDatabaseModel())

        // - insert the featured article
        val dbFeaturedArticle = featuredArticle.toDatabaseModel()
        viWikiDatabase.featuredArticleDao.insert(featuredArticle = dbFeaturedArticle)
    }

    private suspend fun cacheMostRead(mostRead: NetworkMostRead) {
        val databaseMostRead = mostRead.articles.map { networkArticle ->
            val thumbnail = networkArticle.thumbnail?.let {
                // - insert article original image
                DatabaseImage(
                    sourceAndId = it.source, width = it.width, height = it.height
                )
            }
            thumbnail ?: return@map null
            viWikiDatabase.imageDao.insert(thumbnail)

            val originalImage = networkArticle.originalImage?.let {
                // - insert article image
                DatabaseImage(
                    sourceAndId = it.source, width = it.width, height = it.height
                )
            }
            originalImage ?: return@map null
            viWikiDatabase.imageDao.insert(originalImage)

            // - create Article object
            return@map networkArticle.toDatabaseModel(
                isOnThisDay = false,
                isMostRead = true,
                isFeatured = false,
            )
        }
        val mostReadWithoutNulls = mutableListOf<DatabaseArticle>()
        for (element in databaseMostRead) {
            element?.let {
                mostReadWithoutNulls.add(it)
            }
        }
        val mostReadArray = mostReadWithoutNulls.toTypedArray()
        // - insert mostReadArticles
        viWikiDatabase.mostReadArticlesDao.insertAll(*mostReadArray)
    }

    private suspend fun cacheDayImage(dayImage: NetworkDayImage) = with(dayImage) {
        //- insert description
        val dbDescription = DatabaseDescription(
            text = description.text,
            lang = description.lang,
        )
        val insertedDescriptionId = viWikiDatabase.descriptionDao.insert(dbDescription)
        //- insert original
        val dbThumbnail = DatabaseImage(
            sourceAndId = thumbnail.source,
            width = thumbnail.width,
            height = thumbnail.height,
        )
        viWikiDatabase.imageDao.insert(dbThumbnail)
        //- insert full size image
        val dbFullSizeImage = DatabaseImage(
            sourceAndId = image.source,
            width = image.width,
            height = image.height,
        )
        viWikiDatabase.imageDao.insert(dbFullSizeImage)

        // insert DayImage
        val dbDayImage = DatabaseDayImage(
            thumbnailId = dbThumbnail.sourceAndId,
            imageId = dbFullSizeImage.sourceAndId,
            descriptionId = insertedDescriptionId,
            title = title,
        )
        viWikiDatabase.dayImageDao.insert(dbDayImage)
    }
}