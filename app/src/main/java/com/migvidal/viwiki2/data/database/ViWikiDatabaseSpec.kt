package com.migvidal.viwiki2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.migvidal.viwiki2.data.database.entities.Article
import com.migvidal.viwiki2.data.database.entities.DayImage
import com.migvidal.viwiki2.data.database.entities.Description
import com.migvidal.viwiki2.data.database.entities.FeaturedArticle
import com.migvidal.viwiki2.data.database.entities.Image
import com.migvidal.viwiki2.data.database.entities.MostRead
import com.migvidal.viwiki2.data.database.entities.OnThisDay

@Database(
    entities = [Image::class, FeaturedArticle::class, MostRead::class, Article::class, DayImage::class, Description::class, OnThisDay::class],
    version = 1, exportSchema = false
)
abstract class ViWikiDatabaseSpec : RoomDatabase() {
    abstract val featuredArticleDao: FeaturedArticleDao
    abstract val mostReadDao: MostReadDao
    abstract val dayImageDao: DayImageDao
    abstract val onThisDayDao: OnThisDayDao
}

const val DatabaseName = "viwiki_database"

object ViWikiDatabase {
    private lateinit var instance: ViWikiDatabaseSpec

    @JvmStatic
    fun getInstance(applicationContext: Context): ViWikiDatabaseSpec {
        if (!::instance.isInitialized) {
            instance = Room.databaseBuilder(
                context = applicationContext,
                klass = ViWikiDatabaseSpec::class.java,
                name = DatabaseName
            ).fallbackToDestructiveMigration().build()
        }
        return instance
    }

}