package com.example.dailynews.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dailynews.api.NewsApi
import com.example.dailynews.data.ArticleDAO
import com.example.dailynews.data.models.Article
import com.example.dailynews.data.models.NewsList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ArticleRepository(private val articleDAO: ArticleDAO, context: Context, private val api: NewsApi) {

    private val _articleList = MutableLiveData<List<Article>>()
    var articleList: LiveData<List<Article>> = _articleList
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("sharedPreferenceFile", Context.MODE_PRIVATE)

    suspend fun getAllArticles(refresh: Boolean) {
        val cachedArticles = articleDAO.getAllArticlesList()
        val dayRefresh =
            (System.currentTimeMillis() - getLastRefreshedTime()) >= 86400000 // more than 24 hours
        when {
            (!refresh && articleDAO.getAllArticlesList().isNotEmpty() && !dayRefresh) -> {
                Log.e("Data", "from cache")
                _articleList.postValue(cachedArticles)
            }

            (articleDAO.getAllArticlesList().isEmpty() || dayRefresh) -> {
                Log.e("Data", "from API cause cache is empty: " + articleDAO.getAllArticlesList().isEmpty() +
                 ". or cause day refresh $dayRefresh")
                _articleList.postValue(cachedArticles)
                val newDataList = getAllData()
                CoroutineScope(Dispatchers.IO).launch {
                    articleDAO.deleteAll()
                    articleDAO.insertALL(newDataList)
                    Log.e("info", "fromAPI")
                    _articleList.postValue(newDataList)

                }
            }
            (refresh) -> {
                Log.e("Data" , "coming from API - forced refresh")
                val newDataList = getAllData()
                CoroutineScope(Dispatchers.IO).launch {
                    articleDAO.deleteAll()
                    articleDAO.insertALL(newDataList)
                    Log.e("info", "fromAPI")
                    _articleList.postValue(newDataList)

                }
            }
        }

    }

    fun searchInAllArticles(search: String) {
        Log.e("Data", "searching database... ")
        _articleList.postValue(articleDAO.searchInArticles("%$search%"))
    }

    private suspend fun getAllData(): List<Article> = suspendCoroutine { continuation ->
        api.getTopHeadline(50).enqueue(object : Callback<NewsList> {
            override fun onResponse(call: Call<NewsList>, response: Response<NewsList>) {
                if (response.isSuccessful && response.body() != null) {
                    val dataList = response.body()!!.articles.toList()
                    saveRefreshTime(System.currentTimeMillis())
                    continuation.resume(dataList)
                } else {
                    continuation.resume(emptyList())
                }
            }

            override fun onFailure(call: Call<NewsList>, t: Throwable) {
                continuation.resume(emptyList())
            }
        })
    }

    private fun getLastRefreshedTime(): Long {
        return sharedPreferences.getLong("LAST_FETCH_TIME", 0L)
    }

    fun saveRefreshTime(time: Long) {
        sharedPreferences.edit().putLong("LAST_FETCH_TIME", time).apply()
    }

}

