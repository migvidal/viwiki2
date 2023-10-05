package com.migvidal.viwiki2.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FeaturedArticleDao {
    @Insert
    fun insert(featuredArticle: FeaturedArticle)

    @Delete
    fun delete(featuredArticle: FeaturedArticle)

    @Query("SELECT * FROM $FeaturedArticleTable")
    fun getAll(): Flow<FeaturedArticle>
}
@Dao
interface MostReadDao {
    @Insert
    fun insert(mostRead: MostRead)

    @Delete
    fun delete(mostRead: MostRead)

    @Query("SELECT * FROM $FeaturedArticleTable " +
            "JOIN article ON $FeaturedArticleTable.id = article.id")
    fun getMostReadAndArticles(): Flow<Map<MostRead, List<Article>>>
}

@Dao
interface ImageDao {
    @Insert
    fun insertAll(image: Image)

    @Delete
    fun delete(image: Image)

    @Query("SELECT * FROM $DayImageTable")
    fun getAll(): Flow<Image>
}


@Dao
interface OnThisDayDao {
    @Insert
    fun insertAll(vararg onThisDay: OnThisDay)

    @Delete
    fun delete(onThisDay: OnThisDay)

    @Query("SELECT * FROM $OnThisDayTable")
    fun getAll(): Flow<List<OnThisDay>>
}