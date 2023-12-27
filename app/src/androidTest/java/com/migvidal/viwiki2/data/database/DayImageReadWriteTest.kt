package com.migvidal.viwiki2.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.migvidal.viwiki2.data.database.entities.DatabaseDayImage
import com.migvidal.viwiki2.data.database.entities.DatabaseImage
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DayImageReadWriteTest {
    private lateinit var dayImageDao: DayImageDao
    private lateinit var imageDao: ImageDao
    private lateinit var db: ViWikiDatabaseSpec

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ViWikiDatabaseSpec::class.java
        ).build()
        dayImageDao = db.dayImageDao
        imageDao = db.imageDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndReadDayImage() {
        val fakeDbThumbnail = DatabaseImage(
            sourceAndId = "https://url.to.thumbnail.jpg",
            width = 400, height = 400
        )
        val fakeDbImage = DatabaseImage(
            sourceAndId = "https://url.to.original-image.jpg",
            width = 1000, height = 1000
        )
        val fakeDbDayImage = DatabaseDayImage(
            description = "Description",
            thumbnailId = fakeDbThumbnail.sourceAndId,
            titleAndId = fakeDbImage.sourceAndId,
            imageId = "https://url.to.original-image.jpg",
        )
        runBlocking {
            imageDao.insert(fakeDbThumbnail)
            imageDao.insert(fakeDbImage)
            dayImageDao.insert(fakeDbDayImage)
            val selectedDayImage = dayImageDao.getAll().first()
            assert(selectedDayImage == fakeDbDayImage)
        }
    }
}