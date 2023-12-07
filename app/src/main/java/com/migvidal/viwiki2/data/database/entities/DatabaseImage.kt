package com.migvidal.viwiki2.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val DatabaseImageTableName = "image"
@Entity(tableName = DatabaseImageTableName)
data class DatabaseImage(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val sourceAndId: String,
    val width: Int,
    val height: Int,
)