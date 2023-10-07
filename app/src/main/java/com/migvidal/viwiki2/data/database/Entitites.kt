package com.migvidal.viwiki2.data.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


data class DayData(
    val featuredArticle: FeaturedArticle,
    val mostRead: MostRead,
    val image: DayImage,
    val onThisDay: List<OnThisDay>,
)

@Entity(tableName = "image")
data class Image(
    val source: String = "",
    val width: Int = 0,
    val height: Int = 0,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val imageId: Long = 0
}

const val FeaturedArticleTable = "featured_article"

@Entity(
    tableName = FeaturedArticleTable,
    foreignKeys = [
        ForeignKey(
            entity = Image::class,
            parentColumns = ["id"],
            childColumns = ["article_id"]
        )
    ],
)
data class FeaturedArticle(
    @ColumnInfo(name = "article_id") val articleId: Int,//foreign key
    val type: String = "",
    val title: String = "",

    @ColumnInfo(name = "display_title")
    val displayTitle: String = "",

    @ColumnInfo(name = "original_image_id")
    val originalImageId: Long,

    @ColumnInfo(name = "thumbnail_id")
    val thumbnailId: Long,

    val description: String = "",
    val extract: String = "",

    @ColumnInfo(name = "normalized_title")
    val normalizedTitle: String = "",
) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var featuredArticleId: Long = 0
}

@Entity(tableName = "most_read")
data class MostRead(
    val date: String = "",
    //val articlesList: List<Article> = emptyList(),
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val mostReadId: Long = 0
}

@Entity
data class Article(
    val foo: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var articleId: Long = 0
}

const val DayImageTableName = "day_image"

@Entity(tableName = DayImageTableName)
data class DayImage(
    val title: String = "",

    @Embedded(prefix = "thumbnail_")
    val thumbnail: Image = Image(),
    @Embedded(prefix = "image_")
    val image: Image = Image(),
    @ColumnInfo(name = "file_page")
    val filePage: String = "",
    @Embedded
    val description: Description = Description(),
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var dayImageId: Long = 0
}

@Entity
data class Description(
    val text: String = "",
    val lang: String = "",
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var descriptionId: Long = 0
}

const val OnThisDayTableName = "on_this_day"

@Entity(tableName = OnThisDayTableName)
data class OnThisDay(
    val foo: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var onThisDayId: Long = 0
}



