package com.migvidal.viwiki2.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class ViWikiDatabaseSpec : RoomDatabase() {
    abstract fun featuredArticleDao(): FeaturedArticleDao
    abstract fun mostReadDao(): MostReadDao
    abstract fun imageDao(): ImageDao
    abstract fun onThisDayDao(): OnThisDayDao
}

const val DatabaseName = "viwiki_database"

object ViWikiDatabase {
    private lateinit var instance: ViWikiDatabaseSpec

    @JvmStatic
    fun getInstance(aplicationContext: Context): ViWikiDatabaseSpec {
        if (!::instance.isInitialized) {
            instance = Room.databaseBuilder(
                context = aplicationContext,
                klass = ViWikiDatabaseSpec::class.java,
                name = DatabaseName
            ).fallbackToDestructiveMigration().build()
        }
        return instance
    }

}