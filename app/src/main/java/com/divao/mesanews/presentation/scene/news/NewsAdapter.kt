package com.divao.mesanews.presentation.scene.news

import android.content.Context
import android.widget.ImageButton
import com.divao.mesanews.R
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

    private val onFavoriteClickedSubject: PublishSubject<NewsVM> = PublishSubject.create()

    val onFavoriteClicked: Observable<NewsVM>
        get() = onFavoriteClickedSubject

    fun updateNewsList(updatedNewsList: List<NewsVM>) {
        clear()
        addAll(updatedNewsList.map { news ->
            NewsItem(news)
        })
    }

    private inner class NewsItem(private val newsVM: NewsVM) : Item() {
        override fun bind(
            viewHolder: com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder,
            position: Int
        ) {
            viewHolder.apply {
                val progressDrawable = getProgressDrawable(context)

                title.text = newsVM.title
                description.text = newsVM.description
                imageView.loadImage(newsVM.imageUrl, progressDrawable)
                setFavoriteButton(newsVM.isFavorite, favoriteButton)

                favoriteButton.clicks().map {
                    setFavorite(newsVM, favoriteButton)
                    newsVM
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

        fun setFavorite(newsVM: NewsVM, favoriteButton: ImageButton) {
            if (newsVM.isFavorite) {
                setFavoriteButton(false, favoriteButton)
                newsVM.isFavorite = false
            } else {
                setFavoriteButton(true, favoriteButton)
                newsVM.isFavorite = true
            }
        }

        override fun getLayout(): Int = R.layout.item_news
    }
}