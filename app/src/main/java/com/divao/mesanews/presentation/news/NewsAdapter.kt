package com.divao.mesanews.presentation.news

import android.content.Context
import com.divao.mesanews.R
import com.divao.mesanews.model.News
import com.divao.mesanews.util.getProgressDrawable
import com.divao.mesanews.util.loadImage
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_news.*

class NewsAdapter(private val context: Context) :
    GroupAdapter<GroupieViewHolder>() {

    fun updateNewsList(updatedNewsList: List<News>) {
        addAll(updatedNewsList.map { news ->
            NewsItem(news)
        })
    }

    private inner class NewsItem(private val news: News) : Item() {
        override fun bind(
            viewHolder: com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder,
            position: Int
        ) {
            viewHolder.apply {
                val progressDrawable = getProgressDrawable(context)

                title.text = news.title
                description.text = news.description
                imageView.loadImage(news.imageUrl, progressDrawable)
            }
        }

        override fun getLayout(): Int = R.layout.item_news
    }
}