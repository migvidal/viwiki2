package com.migvidal.viwiki2.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

const val DatabaseArticleTableName = "article"
private const val ThumbnailIdColumnName = "thumbnail_id"
private const val OriginalImageIdColumnName = "original_image_id"

@Entity(
    tableName = DatabaseArticleTableName,
    foreignKeys = [
        ForeignKey(
            entity = DatabaseImage::class,
            parentColumns = ["id"],
            childColumns = [ThumbnailIdColumnName]
        ),
        ForeignKey(
            entity = DatabaseImage::class,
            parentColumns = ["id"],
            childColumns = [OriginalImageIdColumnName]
        )
    ],
    indices = [
        Index(ThumbnailIdColumnName),
        Index(OriginalImageIdColumnName),
    ]
)
data class DatabaseArticle(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val articleId: Int,
    val views: Int?,
    val normalizedTitle: String?,
    val description: String?,
    val extract: String?,
    // Foreign keys
    @ColumnInfo(name = ThumbnailIdColumnName)
    val thumbnailId: String?,
    @ColumnInfo(name = OriginalImageIdColumnName)
    val originalImageId: String?,
    // /Foreign keys
    val isFeatured: Boolean = false,
    val isMostRead: Boolean = false,
    val isSaved: Boolean = false,
    val onThisDayYear: Int? = null,
)