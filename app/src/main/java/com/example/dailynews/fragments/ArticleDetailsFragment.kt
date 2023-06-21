package com.example.dailynews.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.UnderlineSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.example.dailynews.R
import com.example.dailynews.data.models.Article
import com.example.dailynews.utils.DateTimeUtil
import com.example.dailynews.utils.ImageLoaderUtil


class ArticleDetailsFragment : Fragment() {
    private lateinit var  context: Context
    private lateinit var article: Article

    private lateinit var titleTV : TextView
    private lateinit var  authorTV: TextView
    private lateinit var  dateTV: TextView
    private lateinit var  articleContentTV: TextView
    private lateinit var  articleImageIV: ImageView



    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_article_details, container, false)

        val args  by navArgs<ArticleDetailsFragmentArgs>()
        article = args.selectedArticle


        titleTV = view.findViewById(R.id.titleTV)
        authorTV = view.findViewById(R.id.authorTV)
        dateTV = view.findViewById(R.id.dateTV)
        articleContentTV = view.findViewById(R.id.articleContentTV)
        articleImageIV = view.findViewById(R.id.articleImageIV)

        titleTV.text = article.title

        authorTV.text = if (article.author != null && article.author!!.isNotEmpty()) {
            buildString {
                append("Author: ")
                append(article.author)
            }
        } else {
            null
        }
        dateTV.text = article.publishedAt?.let {
            DateTimeUtil.formatDate(it)
        }
        if(article.content!=null) {
            val modifiedText = article.content!!.replace("\\s*\\[\\+\\d+\\s+chars]".toRegex(), " Read more")

            val content = SpannableString(modifiedText)
            content.setSpan(UnderlineSpan(), content.indexOf("Read more"), content.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            articleContentTV.text = content
            articleContentTV.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(article.url)
                startActivity(intent)
            }
        }

        ImageLoaderUtil.loadImages(articleImageIV, article.urlToImage ?: "", context)


        return  view
    }

}