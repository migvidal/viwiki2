package com.migvidal.viwiki2.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val ImageTableName = "image"
@Entity(tableName = ImageTableName)
data class DatabaseImage(
    val source: String = "",
    val width: Int = 0,
    val height: Int = 0,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var imageId: Long = 0
}