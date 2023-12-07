package com.migvidal.viwiki2.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

const val DatabaseArticleTableName = "article"
private const val ThumbnailIdColumnName = "thumbnail_row_id"
private const val OriginalImageIdColumnName = "original_image_row_id"

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
    val normalizedTitle: String,
    val description: String,
    val extract: String,
    // Foreign keys
    @ColumnInfo(name = ThumbnailIdColumnName)
    val thumbnailRowId: Long,
    @ColumnInfo(name = OriginalImageIdColumnName)
    val originalImageRowId: Long,
    // /Foreign keys
    val isFeatured: Boolean = false,
    val isMostRead: Boolean = false,
    val isOnThisDay: Boolean = false,
)