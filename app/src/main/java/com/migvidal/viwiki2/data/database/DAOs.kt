package com.migvidal.viwiki2.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.migvidal.viwiki2.data.database.entities.ArticleTableName
import com.migvidal.viwiki2.data.database.entities.DatabaseArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseDayImage
import com.migvidal.viwiki2.data.database.entities.DatabaseFeaturedArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseImage
import com.migvidal.viwiki2.data.database.entities.DatabaseMostRead
import com.migvidal.viwiki2.data.database.entities.DatabaseOnThisDay
import com.migvidal.viwiki2.data.database.entities.DayImageTableName
import com.migvidal.viwiki2.data.database.entities.FeaturedArticleTableName
import com.migvidal.viwiki2.data.database.entities.ImageTableName
import com.migvidal.viwiki2.data.database.entities.MostReadTableName
import com.migvidal.viwiki2.data.database.entities.OnThisDayTableName
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(databaseImage: DatabaseImage): Long

    @Delete
    suspend fun delete(databaseImage: DatabaseImage)

    @Query("SELECT * FROM $ImageTableName")
    fun getAll(): Flow<DatabaseImage?>
}

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg databaseArticle: DatabaseArticle)

    @Delete
    suspend fun delete(databaseArticle: DatabaseArticle)

    @Query("SELECT * FROM $ArticleTableName")
    fun getAll(): Flow<DatabaseArticle?>
}

@Dao
interface FeaturedArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(featuredArticle: DatabaseFeaturedArticle): Long

    @Delete
    suspend fun delete(databaseFeaturedArticle: DatabaseFeaturedArticle)

    @Query("SELECT * FROM $FeaturedArticleTableName")
    fun getAll(): Flow<DatabaseFeaturedArticle?>
}
@Dao
interface MostReadDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(databaseMostRead: DatabaseMostRead): Long

    @Delete
    fun delete(databaseMostRead: DatabaseMostRead)

    @Query("SELECT * FROM $MostReadTableName")
    fun getMostRead(): Flow<DatabaseMostRead?>
}

@Dao
interface DayImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(databaseDayImage: DatabaseDayImage): Long

    @Delete
    fun delete(databaseDayImage: DatabaseDayImage)

    @Query("SELECT * FROM $DayImageTableName")
    fun getAll(): Flow<DatabaseDayImage?>
}

@Dao
interface OnThisDayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(databaseOnThisDay: DatabaseOnThisDay): Long

    @Delete
    fun delete(databaseOnThisDay: DatabaseOnThisDay)

    @Query("SELECT * FROM $OnThisDayTableName " +
            "JOIN article")
    fun getAll(): Flow<List<DatabaseOnThisDay>?>
}