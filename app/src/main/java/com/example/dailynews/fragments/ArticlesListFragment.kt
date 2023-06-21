package com.example.dailynews.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.dailynews.R
import com.example.dailynews.adapters.ArticlesAdapter
import com.example.dailynews.data.models.Article
import com.example.dailynews.data.viewmodel.ArticleViewModel


class ArticlesListFragment : Fragment() {

    private var data : List<Article>? = listOf()
    private lateinit var  articlesRV : RecyclerView
    private lateinit var  swipeRefreshLayout : SwipeRefreshLayout
    private lateinit var  searchArticlesET : EditText
    private  val  articleViewModel : ArticleViewModel by viewModels()
    private lateinit var context: Context

    private val articleAdapter by lazy {
        ArticlesAdapter()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view =  inflater.inflate(R.layout.fragment_articles_list, container, false)
        articlesRV = view.findViewById(R.id.articlesRV)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        searchArticlesET = view.findViewById(R.id.searchArticlesET)
        articlesRV.adapter = articleAdapter
        articlesRV.layoutManager = LinearLayoutManager(context)

        articleViewModel.getAllArticles(false)
        articleViewModel.newsList.observe(viewLifecycleOwner, Observer {
            data = it
            Log.e("received", "data")
            if(data!=null) {
                articleAdapter.setData(data!!)
                swipeRefreshLayout.isRefreshing = false
            }
        })
        swipeRefreshLayout.setOnRefreshListener {
            articleViewModel.getAllArticles(true)
        }

        searchArticlesET.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(s!=null) {
                    articleViewModel.searchInArticles(s.toString())
                    articleViewModel.filteredList.observe(viewLifecycleOwner, Observer {
                        if(it!=null) {
                            data = it
                            articleAdapter.setData(data!!)
                        }
                    })
                }
            }
        }

        )

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}