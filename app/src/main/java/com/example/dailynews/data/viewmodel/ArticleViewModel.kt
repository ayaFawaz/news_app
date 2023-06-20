package com.example.dailynews.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dailynews.api.ApiService
import com.example.dailynews.data.ArticleDataBase
import com.example.dailynews.data.models.Article
import com.example.dailynews.data.repository.ArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleViewModel(application: Application) : AndroidViewModel(application) {

    private val articleDAO = ArticleDataBase.getDatabase(application).articleDAO()
    private val repository: ArticleRepository = ArticleRepository(articleDAO, application.applicationContext, ApiService.getNewsApi())
    private val _newsList = MutableLiveData<List<Article>>()
    var newsList: LiveData<List<Article>> = _newsList
    private var _filteredList = MutableLiveData<List<Article>>()
    var filteredList: LiveData<List<Article>> = _filteredList


    init {
        newsList = repository.articleList
        filteredList = repository.articleList
    }

    fun searchInArticles(search: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchInAllArticles(search)
        }

    }

    fun getAllArticles(reload: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllArticles(reload)
        }
    }
}