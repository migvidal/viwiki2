package com.migvidal.viwiki2.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.migvidal.viwiki2.data.database.entities.Article
import com.migvidal.viwiki2.data.database.entities.ArticleTableName
import com.migvidal.viwiki2.data.database.entities.DayImage
import com.migvidal.viwiki2.data.database.entities.DayImageTableName
import com.migvidal.viwiki2.data.database.entities.FeaturedArticle
import com.migvidal.viwiki2.data.database.entities.FeaturedArticleTableName
import com.migvidal.viwiki2.data.database.entities.Image
import com.migvidal.viwiki2.data.database.entities.ImageTableName
import com.migvidal.viwiki2.data.database.entities.MostRead
import com.migvidal.viwiki2.data.database.entities.MostReadTableName
import com.migvidal.viwiki2.data.database.entities.OnThisDay
import com.migvidal.viwiki2.data.database.entities.OnThisDayTableName
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(image: Image): Long

    @Delete
    suspend fun delete(image: Image)

    @Query("SELECT * FROM $ImageTableName")
    fun getAll(): Flow<Image?>
}

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article): Long

    @Delete
    suspend fun delete(article: Article)

    @Query("SELECT * FROM $ArticleTableName")
    fun getAll(): Flow<Article?>
}

@Dao
interface FeaturedArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(featuredArticle: FeaturedArticle): Long

    @Delete
    suspend fun delete(featuredArticle: FeaturedArticle)

    @Query("SELECT * FROM $FeaturedArticleTableName")
    fun getAll(): Flow<FeaturedArticle?>
}
@Dao
interface MostReadDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mostRead: MostRead): Long

    @Delete
    fun delete(mostRead: MostRead)

    @Query("SELECT * FROM $MostReadTableName")
    fun getMostRead(): Flow<MostRead?>
}

@Dao
interface DayImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dayImage: DayImage): Long

    @Delete
    fun delete(dayImage: DayImage)

    @Query("SELECT * FROM $DayImageTableName")
    fun getAll(): Flow<DayImage?>
}

@Dao
interface OnThisDayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(onThisDay: OnThisDay): Long

    @Delete
    fun delete(onThisDay: OnThisDay)

    @Query("SELECT * FROM $OnThisDayTableName " +
            "JOIN article")
    fun getAll(): Flow<List<OnThisDay>?>
}