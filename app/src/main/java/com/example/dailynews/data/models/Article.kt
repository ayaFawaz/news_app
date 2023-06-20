package com.example.dailynews.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey


@kotlinx.parcelize.Parcelize
@Entity(tableName = "article_table")
data class Article(
    val source: Source?,
    val author: String?,
    val title: String?,
    val description: String?,
    @PrimaryKey
    val url: String,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
) : Parcelable
/*
since source's id is not unique to the article and the article itself does not have a unique ID, then I chose
url to be the unique key
* */