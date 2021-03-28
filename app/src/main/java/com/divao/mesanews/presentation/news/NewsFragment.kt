package com.divao.mesanews.presentation.news

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.divao.mesanews.R
import com.divao.mesanews.model.News
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment(), NewsView {

    companion object {
        fun newInstance(): NewsFragment = NewsFragment()
    }

    private val presenter = NewsPresenter(this)
    private lateinit var newsAdapter: NewsAdapter

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_news, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.fetchNews()
        initViews()
    }

    private fun initViews() {
        newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            presenter.fetchNews()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        newsAdapter = NewsAdapter(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onViewDestroyed()
    }

}