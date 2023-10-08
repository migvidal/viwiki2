package com.migvidal.viwiki2.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

const val DayImageTableName = "day_image"

private const val ThumbnailIdColumnName = "thumbnail_id"
private const val ImageIdColumnName = "image_id"
private const val DescriptionIdColumnName = "description_id_column_name"

@Entity(
    tableName = DayImageTableName,
    foreignKeys = [
        ForeignKey(
            entity = DatabaseImage::class,
            parentColumns = ["id"],
            childColumns = [ThumbnailIdColumnName]
        ),
        ForeignKey(
            entity = DatabaseImage::class,
            parentColumns = ["id"],
            childColumns = [ImageIdColumnName]
        ),
        ForeignKey(
            entity = DatabaseDescription::class,
            parentColumns = ["id"],
            childColumns = [DescriptionIdColumnName]
        ),
    ],
    indices = [
        Index(ThumbnailIdColumnName),
        Index(ImageIdColumnName),
        Index(DescriptionIdColumnName),
    ],
)
data class DatabaseDayImage(
    // Foreign keys
    @ColumnInfo(name = ThumbnailIdColumnName)
    val thumbnailId: Long,

    @ColumnInfo(name = ImageIdColumnName)
    val imageId: Long,

    @ColumnInfo(name = DescriptionIdColumnName)
    val descriptionId: Long,
    // /Foreign keys

    val title: String = "",

    @ColumnInfo(name = "file_page")
    val filePage: String = "",

    ) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var dayImageId: Long = 0
}