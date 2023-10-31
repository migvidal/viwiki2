package com.migvidal.viwiki2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.migvidal.viwiki2.data.database.entities.DatabaseArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseDayImage
import com.migvidal.viwiki2.data.database.entities.DatabaseDescription
import com.migvidal.viwiki2.data.database.entities.DatabaseImage
import com.migvidal.viwiki2.data.database.entities.DatabaseOnThisDay

@Database(
    entities = [
        DatabaseImage::class,
        DatabaseArticle::class,
        DatabaseDayImage::class,
        DatabaseDescription::class,
        DatabaseOnThisDay::class
    ],
    version = 1, exportSchema = false
)
abstract class ViWikiDatabaseSpec : RoomDatabase() {
    abstract val imageDao: ImageDao
    abstract val descriptionDao: DescriptionDao
    abstract val articleDao: ArticleDao
    abstract val featuredArticleDao: FeaturedArticleDao
    abstract val mostReadArticlesDao: MostReadArticleListDao
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