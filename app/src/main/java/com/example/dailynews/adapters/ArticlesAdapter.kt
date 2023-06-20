package com.example.dailynews.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.dailynews.R
import com.example.dailynews.data.models.Article
import com.example.dailynews.utils.DateTimeUtil
import com.example.dailynews.fragments.ArticlesListFragmentDirections

class ArticlesAdapter() : RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

    private var articles = emptyList<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.articleTitleTV.text = articles[position].title
        holder.articleDescriptionTV.text = articles[position].description
        holder.articleDateTV.text =
            articles[position].publishedAt?.let { DateTimeUtil.formatDate(it) }
        holder.containerLL.setOnClickListener {
            val action = ArticlesListFragmentDirections.actionListToDetails(articles[position])
           holder.itemView.findNavController().navigate(action)
        }

    }

    class ArticleViewHolder(itemView: View) : ViewHolder(itemView) {
        var articleTitleTV: TextView = itemView.findViewById(R.id.articleTitleTV)
        var articleDateTV: TextView = itemView.findViewById(R.id.articleDateTV)
        var articleDescriptionTV: TextView = itemView.findViewById(R.id.articleDescriptionTV)
        var containerLL: LinearLayout = itemView.findViewById(R.id.containerLL)


    }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun setData(data: List<Article>) {
        this.articles = data
        notifyDataSetChanged()
    }


}