package com.example.dailynews.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dailynews.data.models.Article

@Dao
interface ArticleDAO {
    @Query("SELECT * FROM article_table" )
     fun getAllArticles() : LiveData<List<Article>>

     @Query("SELECT * FROM article_table")
     fun getAllArticlesList() : List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertALL(articles : List<Article>)

    @Query("DELETE FROM article_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM article_table WHERE title LIKE :search " +
            "OR description LIKE :search " +
            "OR author LIKE :search " +
            "OR content LIKE :search")
    fun searchInArticles(search: String): List<Article>
}