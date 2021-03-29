package com.divao.mesanews.presentation.news

import android.content.Context
import android.widget.ImageButton
import com.divao.mesanews.R
import com.divao.mesanews.model.News
import com.divao.mesanews.util.getProgressDrawable
import com.divao.mesanews.util.loadImage
import com.jakewharton.rxbinding3.view.clicks
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_news.*

class NewsAdapter(private val context: Context) :
    GroupAdapter<GroupieViewHolder>() {

    private val onFavoriteClickedSubject: PublishSubject<News> = PublishSubject.create()

    val onFavoriteClicked: Observable<News>
        get() = onFavoriteClickedSubject

    fun updateNewsList(updatedNewsList: List<News>) {
        clear()
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
                setFavoriteButton(news.isFavorite, favoriteButton)

                favoriteButton.clicks().map {
                    setFavorite(news, favoriteButton)
                    news
                }.subscribe(onFavoriteClickedSubject)
            }
        }

        fun setFavoriteButton(isFavorite: Boolean, favoriteButton: ImageButton) {
            if (isFavorite) {
                favoriteButton.setImageResource(R.drawable.filled_heart)
            } else {
                favoriteButton.setImageResource(R.drawable.empty_heart)
            }
        }

        fun setFavorite(news: News, favoriteButton: ImageButton) {
            if (news.isFavorite) {
                setFavoriteButton(false, favoriteButton)
                news.isFavorite = false
            } else {
                setFavoriteButton(true, favoriteButton)
                news.isFavorite = true
            }
        }

        override fun getLayout(): Int = R.layout.item_news
    }
}