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
            entity = Article::class,
            parentColumns = ["id"],
            childColumns = [ArticleIdColumnName]
        ),
        ForeignKey(
            entity = Image::class,
            parentColumns = ["id"],
            childColumns = [OriginalImageIdColumnName]
        ),
        ForeignKey(
            entity = Image::class,
            parentColumns = ["id"],
            childColumns = [ThumbnailIdColumnName]
        )
    ],
    indices = [
        Index(ArticleIdColumnName),
        Index(OriginalImageIdColumnName),
        Index(ThumbnailIdColumnName),
    ]
)
data class FeaturedArticle(
    // Foreign keys
    @ColumnInfo(name = ArticleIdColumnName)
    val articleId: Long = 0,
    @ColumnInfo(name = OriginalImageIdColumnName)
    val originalImageId: Long = 0,
    @ColumnInfo(name = ThumbnailIdColumnName)
    val thumbnailId: Long = 0,
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