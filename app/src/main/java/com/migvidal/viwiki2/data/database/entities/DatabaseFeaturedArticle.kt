package com.migvidal.viwiki2.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

const val FeaturedArticleTableName = "featured_article"

const val ArticleIdColumnName = "article_id"
const val OriginalImageIdColumnName = "original_image_id"
private const val ThumbnailIdColumnName = "thumbnail_id"

@Entity(
    tableName = FeaturedArticleTableName,
    foreignKeys = [
        ForeignKey(
            entity = DatabaseImage::class,
            parentColumns = ["id"],
            childColumns = [OriginalImageIdColumnName]
        ),
        ForeignKey(
            entity = DatabaseImage::class,
            parentColumns = ["id"],
            childColumns = [ThumbnailIdColumnName]
        )
    ],
    indices = [
        Index(OriginalImageIdColumnName),
        Index(ThumbnailIdColumnName),
    ]
)
data class DatabaseFeaturedArticle(
    // Foreign keys
    @ColumnInfo(name = OriginalImageIdColumnName)
    val originalImageId: Long,
    @ColumnInfo(name = ThumbnailIdColumnName)
    val thumbnailId: Long,
    // /Foreign keys

    val type: String = "",

    val title: String = "",

    @ColumnInfo(name = "display_title")
    val displayTitle: String = "",

    val description: String = "",

    val extract: String = "",

    @ColumnInfo(name = "normalized_title")
    val normalizedTitle: String = "",
) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var featuredArticleId: Long = 0
}