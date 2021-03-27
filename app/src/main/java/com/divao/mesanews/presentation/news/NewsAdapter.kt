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

class NewsAdapter(val context: Context, var newsList: ArrayList<News>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    fun updateNewsList(updatedNewsList: List<News>) {
        newsList.clear()
        newsList.addAll(updatedNewsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)

        return NewsViewHolder(view)
    }

    override fun getItemCount() = newsList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(context, newsList[position], position)
    }

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.imageView
        private val title = view.title
        private val description = view.description
        private val progressDrawable = getProgressDrawable(view.context)

        fun bind(context: Context, news: News, position: Int) {
            title.text = news.title
            description.text = news.description
            imageView.loadImage(news.imageUrl, progressDrawable)
        }
    }
}