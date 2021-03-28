package com.divao.mesanews.presentation.news

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.divao.mesanews.FlowContainerFragment
import com.divao.mesanews.MNApplication
import com.divao.mesanews.R
import com.divao.mesanews.model.News
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_news.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class NewsFragment : Fragment(), NewsView {

    companion object {
        fun newInstance(): NewsFragment = NewsFragment()
    }

    private val disposeBag = CompositeDisposable()

    @Inject
    lateinit var presenter: NewsPresenter

    @Inject
    lateinit var router: Router

    private lateinit var newsAdapter: NewsAdapter

    override val onViewLoaded: PublishSubject<String> = PublishSubject.create()

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

        newsAdapter.onFavoriteClicked.subscribe { newsId ->
            presenter.setFavorite(newsId)
        }.addTo(disposeBag)

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            presenter.fetchNews()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        newsAdapter = NewsAdapter(context)
        activity?.let {
            DaggerNewsComponent.builder()
                .newsModule(NewsModule(this))
                .flowComponent((parentFragment as FlowContainerFragment).component)
                .build()
                .inject(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onViewDestroyed()
        disposeBag.clear()
    }

}