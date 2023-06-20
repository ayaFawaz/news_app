package com.example.dailynews.data.models

import com.example.dailynews.data.models.Article


data class NewsList(
    val status: String,
    val totalResult: Int,
    val articles: ArrayList<Article>
)
