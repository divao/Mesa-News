package com.divao.mesanews.presentation.news

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.divao.mesanews.R
import com.divao.mesanews.model.News
import com.divao.mesanews.util.getProgressDrawable
import com.divao.mesanews.util.loadImage
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(private val context: Context) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var newsList: List<News> = emptyList()

    fun updateNewsList(updatedNewsList: List<News>) {
        newsList = updatedNewsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)

        return NewsViewHolder(view)
    }

    override fun getItemCount() = newsList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(context, newsList[position])
    }

    class NewsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val progressDrawable = getProgressDrawable(view.context)

        fun bind(context: Context, news: News) {
            view.title.text = news.title
            view.description.text = news.description
            view.imageView.loadImage(news.imageUrl, progressDrawable)
        }
    }
}