package com.migvidal.viwiki2.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val ArticleTableName = "article"
@Entity(tableName = ArticleTableName)
data class Article(
    val foo: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var articleId: Long = 0
}