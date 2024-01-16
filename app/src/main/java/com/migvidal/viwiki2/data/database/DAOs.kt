package com.migvidal.viwiki2.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.migvidal.viwiki2.data.database.entities.DatabaseArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseArticleTableName
import com.migvidal.viwiki2.data.database.entities.DatabaseDayImage
import com.migvidal.viwiki2.data.database.entities.DatabaseImage
import com.migvidal.viwiki2.data.database.entities.DatabaseImageTableName
import com.migvidal.viwiki2.data.database.entities.DatabaseOnThisDay
import com.migvidal.viwiki2.data.database.entities.DayImageTableName
import com.migvidal.viwiki2.data.database.entities.OnThisDayTableName
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(databaseImage: DatabaseImage): Long

    @Delete
    suspend fun delete(databaseImage: DatabaseImage)

    @Query("SELECT * FROM $DatabaseImageTableName")
    fun getAll(): Flow<DatabaseImage?>

    @Query(
        "SELECT * FROM $DatabaseImageTableName" +
                " WHERE $DatabaseImageTableName.id = :id"
    )
    suspend fun getImageById(id: String): DatabaseImage?
}

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg databaseArticle: DatabaseArticle)

    @Delete
    suspend fun delete(databaseArticle: DatabaseArticle)

    @Query("SELECT * FROM $DatabaseArticleTableName")
    fun getAll(): Flow<List<DatabaseArticle?>>

    @Query(
        "SELECT * FROM $DatabaseArticleTableName" +
                " WHERE $DatabaseArticleTableName.isSaved = 1"
    )
    fun getSavedArticles(): Flow<List<DatabaseArticle?>>

    @Query(
        "SELECT * FROM $DatabaseArticleTableName" +
                " WHERE $DatabaseArticleTableName.id = :id"
    )
    fun getArticleById(id: Int): DatabaseArticle?
}

@Dao
interface FeaturedArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(featuredArticle: DatabaseArticle): Long

    @Delete
    suspend fun delete(featuredArticle: DatabaseArticle)

    @Query(
        "SELECT * FROM $DatabaseArticleTableName" +
                " WHERE $DatabaseArticleTableName.isFeatured = 1"
    )
    fun getAll(): Flow<DatabaseArticle?>
}

@Dao
interface MostReadArticleListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg mostReadArticles: DatabaseArticle): List<Long>

    @Delete
    fun delete(mostReadArticles: DatabaseArticle)

    @Query(
        "SELECT * FROM $DatabaseArticleTableName" +
                " WHERE $DatabaseArticleTableName.isMostRead = 1" //1 is 'true' in Sqlite
    )
    fun getMostRead(): Flow<List<DatabaseArticle>>
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
    suspend fun insertAll(vararg databaseOnThisDay: DatabaseOnThisDay)

    @Delete
    fun delete(databaseOnThisDay: DatabaseOnThisDay)

    @Query("SELECT * FROM $OnThisDayTableName")
    fun getAllOnThisDay(): Flow<List<DatabaseOnThisDay>?>

    @Query(
        "SELECT * FROM $DatabaseArticleTableName" +
                " WHERE $DatabaseArticleTableName.onThisDayYear = :year"
    )
    suspend fun getArticlesForYear(year: Int): List<DatabaseArticle>?
}