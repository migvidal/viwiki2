package com.migvidal.viwiki2.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val DatabaseArticleTableName = "article"
@Entity(tableName = DatabaseArticleTableName)
data class DatabaseArticle(
    val normalizedTitle: String = "",
    val description: String,
    val extract: String,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var articleId: Long = 0
}