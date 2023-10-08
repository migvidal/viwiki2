package com.migvidal.viwiki2.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "description")
data class Description(
    val text: String = "",
    val lang: String = "",
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var descriptionId: Long = 0
}