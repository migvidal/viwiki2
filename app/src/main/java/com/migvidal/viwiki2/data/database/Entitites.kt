package com.migvidal.viwiki2.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



data class DayData(
    val featuredArticle: FeaturedArticle,
    val mostRead: MostRead,
    val image: DayImage,
    val onThisDay: List<OnThisDay>,
)


@Entity
data class Image(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val source: String,
    val width: Int,
    val height: Int,
)

const val FeaturedArticleTable = "featured_article"
@Entity(tableName = FeaturedArticleTable)
data class FeaturedArticle(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String,
    val title: String,
    @ColumnInfo(name = "display_title") val displayTitle: String,
    @ColumnInfo(name = "original_image") val originalImage: Image,
    val thumbnail: Image,
    val description: String,
    val extract: String,
    @ColumnInfo(name = "normalized_title") val normalizedTitle: String,
)

const val MostReadTable = "most_read"
@Entity(tableName = MostReadTable)
data class MostRead(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val articles: List<Article>,
)

@Entity
data class Article(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val foo: String
)

const val DayImageTable = "day_image"
@Entity(tableName = DayImageTable)
data class DayImage(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val thumbnail: Image,
    val image: Image,
    @ColumnInfo(name = "file_page") val filePage: String,
    val description: Description,
)

@Entity
data class Description(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val text: String,
    val lang: String,
)

const val OnThisDayTable = "on_this_day"
@Entity(tableName = OnThisDayTable)
data class OnThisDay(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val foo: String
)



