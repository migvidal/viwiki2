package com.migvidal.viwiki2.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.migvidal.viwiki2.data.database.entities.DatabaseArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseArticleTableName
import com.migvidal.viwiki2.data.database.entities.DatabaseDayImage
import com.migvidal.viwiki2.data.database.entities.DatabaseDescription
import com.migvidal.viwiki2.data.database.entities.DatabaseDescriptionTableName
import com.migvidal.viwiki2.data.database.entities.DatabaseFeaturedArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseImage
import com.migvidal.viwiki2.data.database.entities.DatabaseMostReadArticle
import com.migvidal.viwiki2.data.database.entities.DatabaseOnThisDay
import com.migvidal.viwiki2.data.database.entities.DayImageTableName
import com.migvidal.viwiki2.data.database.entities.FeaturedArticleTableName
import com.migvidal.viwiki2.data.database.entities.ImageTableName
import com.migvidal.viwiki2.data.database.entities.MostReadArticleTableName
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

    @Query("SELECT * FROM $ImageTableName " +
            "WHERE $ImageTableName.id = :id")
    suspend fun getImageById(id: Long): DatabaseImage?
}

@Dao
interface DescriptionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(databaseDescription: DatabaseDescription): Long

    @Delete
    suspend fun delete(databaseDescription: DatabaseDescription)

    @Query("SELECT * FROM $DatabaseDescriptionTableName " +
            "WHERE $DatabaseDescriptionTableName.id = :id")
    suspend fun getDescriptionById(id: Long): DatabaseDescription?
}


@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg databaseArticle: DatabaseArticle): List<Long>

    @Delete
    suspend fun delete(databaseArticle: DatabaseArticle)

    @Query("SELECT * FROM $DatabaseArticleTableName")
    fun getAll(): Flow<DatabaseArticle?>
}

@Dao
interface FeaturedArticlesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(featuredArticle: DatabaseFeaturedArticle): Long

    @Delete
    suspend fun delete(databaseFeaturedArticle: DatabaseFeaturedArticle)

    @Query("SELECT * FROM $FeaturedArticleTableName")
    fun getAll(): Flow<DatabaseFeaturedArticle?>
}
@Dao
interface MostReadArticleListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg mostReadArticles: DatabaseMostReadArticle): List<Long>

    @Delete
    fun delete(mostReadArticles: DatabaseMostReadArticle)

    @Query("SELECT * FROM $MostReadArticleTableName " +
            "JOIN $DatabaseArticleTableName " +
            "ON $DatabaseArticleTableName.id = $MostReadArticleTableName.article_id"
    )
    fun getMostReadAndArticles(): Flow<Map<DatabaseMostReadArticle, List<DatabaseArticle>>>
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

    @Query("SELECT * FROM $OnThisDayTableName")
    fun getAll(): Flow<List<DatabaseOnThisDay>?>
}