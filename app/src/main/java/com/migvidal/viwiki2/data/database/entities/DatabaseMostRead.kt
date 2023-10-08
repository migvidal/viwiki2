package com.migvidal.viwiki2.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val MostReadTableName = "most_read"
@Entity(tableName = MostReadTableName)
data class DatabaseMostRead(
    val date: String = "",
    //val articlesList: List<Article> = emptyList(),
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var mostReadId: Long = 0
}