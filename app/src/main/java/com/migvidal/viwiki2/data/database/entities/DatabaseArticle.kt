package com.migvidal.viwiki2.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

const val DatabaseArticleTableName = "article"
private const val ThumbnailIdColumnName = "thumbnail_id"
@Entity(tableName = DatabaseArticleTableName,
    foreignKeys = [
        ForeignKey(
            entity = DatabaseImage::class,
            parentColumns = ["id"],
            childColumns = [ThumbnailIdColumnName]
        )],
    indices = [Index(ThumbnailIdColumnName)]
)
data class DatabaseArticle(
    val views: Int?,
    val normalizedTitle: String = "",
    val description: String,
    val extract: String,
    @ColumnInfo(name = ThumbnailIdColumnName)
    val thumbnailId: Long?,
    val isMostRead: Boolean,
    val isOnThisDay: Boolean,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var articleId: Long = 0
}