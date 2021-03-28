package com.divao.mesanews.presentation.news

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.divao.mesanews.R
import com.divao.mesanews.model.News
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity(), NewsView {

    private val presenter = NewsPresenter(this)
    private val newsAdapter = NewsAdapter(this)

    override fun displayLoading() {
        errorList.visibility = View.GONE
        newsRecyclerView.visibility = View.GONE
        loadingView.visibility = View.VISIBLE
    }

    override fun dismissLoading() {
        loadingView.visibility = View.GONE
    }

    override fun displayNewsList(newsList: List<News>) {
        errorList.visibility = View.GONE
        newsRecyclerView.visibility = View.VISIBLE
        newsAdapter.updateNewsList(newsList)
    }

    override fun displayError() {
        errorList.visibility = View.VISIBLE
        newsRecyclerView.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        presenter.fetchNews()

        newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            presenter.fetchNews()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }
}