@file:Suppress("ConstPropertyName")

package com.migvidal.viwiki2.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


const val MostReadArticleTableName = "most_read_article"
const val ArticleIdForeignKeyName = "article_id"

@Entity(
    tableName = MostReadArticleTableName,
    foreignKeys = [ForeignKey(
        entity = DatabaseArticle::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf(ArticleIdForeignKeyName)
    )],
    indices = [Index(ArticleIdForeignKeyName)]
)
data class DatabaseMostReadArticle(
    // Foreign keys
    @ColumnInfo(name = ArticleIdForeignKeyName)
    val articleId: Long,
    // /Foreign keys
    val date: String,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var mostReadArticleId: Long = 0
}
