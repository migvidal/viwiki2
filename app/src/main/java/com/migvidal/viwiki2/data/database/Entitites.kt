package com.migvidal.viwiki2.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/*
data class DayResponse(
    val featuredArticle: FeaturedArticle?,
    val mostRead: MostRead?,
    val image: DayImage?,
    val onThisDay: List<OnThisDay>?,
)
*/

@Entity
data class Image(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val source: String,
    val width: Int,
    val height: Int,
)

@Entity(tableName = "featured_article")
data class FeaturedArticle(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val type: String,
    val title: String,
    @ColumnInfo(name = "display_title") val displayTitle: String,
    @ColumnInfo(name = "original_image") val originalImage: Image,
    val thumbnail: Image,
    val description: String,
    val extract: String,
    @ColumnInfo(name = "normalized_title") val normalizedTitle: String,
)

@Entity(tableName = "most_read")
data class MostRead(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val date: String,
    val articles: List<Article>,
)

@Entity
data class Article(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val foo: String
)

@Entity(tableName = "day_image")
data class DayImage(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val thumbnail: Image,
    val image: Image,
    @ColumnInfo(name = "file_page") val filePage: String,
    val description: Description,
)

@Entity
data class Description(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val text: String,
    val lang: String,
)

@Entity(tableName = "on_this_day")
data class OnThisDay(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val foo: String
)



