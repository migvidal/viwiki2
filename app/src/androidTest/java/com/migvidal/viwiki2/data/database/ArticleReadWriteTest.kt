package com.migvidal.viwiki2.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.migvidal.viwiki2.data.database.entities.DatabaseArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseImage
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ArticleReadWriteTest {
    private val articleId = 100
    private lateinit var articleDao: ArticleDao
    private lateinit var imageDao: ImageDao
    private lateinit var db: ViWikiDatabaseSpec

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ViWikiDatabaseSpec::class.java
        ).build()
        articleDao = db.articleDao
        imageDao = db.imageDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndReadArticle() {
        val fakeDbThumbnail = DatabaseImage(
            sourceAndId = "https://url.to.thumbnail.jpg",
            width = 400, height = 400
        )
        val fakeDbImage = DatabaseImage(
            sourceAndId = "https://url.to.original-image.jpg",
            width = 1000, height = 1000
        )
        val fakeDbArticle = DatabaseArticle(
            articleId = articleId,
            views = 20_000,
            normalizedTitle = "Normalized title",
            description = "Description",
            extract = "Extract",
            thumbnailId = fakeDbThumbnail.sourceAndId,
            originalImageId = fakeDbImage.sourceAndId,
            isFeatured = false,
            isMostRead = false,
            onThisDayYear = 2000,
        )
        runBlocking {
            imageDao.insert(fakeDbThumbnail)
            imageDao.insert(fakeDbImage)
            articleDao.insertAll(fakeDbArticle)
        }
        val articleById = articleDao.getArticleById(articleId)
        assert(articleById == fakeDbArticle)
    }
}