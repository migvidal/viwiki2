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
    val views: Int?,
    val normalizedTitle: String = "",
    val description: String,
    val extract: String,
    @ColumnInfo(name = ThumbnailIdColumnName)
    val thumbnailId: Long?,
    @ColumnInfo(name = OriginalImageIdColumnName)
    val originalImageId: Long?,
    val isFeatured: Boolean = false,
    val isMostRead: Boolean = false,
    val isOnThisDay: Boolean = false,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var articleId: Long = 0
}