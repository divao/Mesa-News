package com.divao.mesanews.presentation.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.divao.mesanews.R
import com.divao.mesanews.presentation.common.navigation.MNScreen
import kotlinx.android.synthetic.main.fragment_main_content.*

class MainContentFragment : Fragment(), ExitHandler {
    override fun onBackPressed() {
        (childFragmentManager.findFragmentByTag(currentlyShowingFragmentTag) as? ExitHandler)?.onBackPressed()
    }

    companion object {
        fun newInstance() = MainContentFragment()
        private const val NEWS_FLOW_FRAGMENT_TAG = "NEWS_FLOW_FRAGMENT_TAG"
        private const val FAVORITES_FLOW_FRAGMENT_TAG = "FAVORITES_FLOW_FRAGMENT_TAG"
    }

    private val newsFCFragment: FlowContainerFragment by lazy {
        childFragmentManager.findFragmentByTag(NEWS_FLOW_FRAGMENT_TAG) as? FlowContainerFragment
            ?: FlowContainerFragment.newInstance(MNScreen.NewsScreen())
    }
    private val favoritesFCFragment: FlowContainerFragment by lazy {
        childFragmentManager.findFragmentByTag(FAVORITES_FLOW_FRAGMENT_TAG) as? FlowContainerFragment
            ?: FlowContainerFragment.newInstance(MNScreen.FavoriteNewsScreen())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_content, container, false)
    }

    private var currentlyShowingFragmentTag: String = NEWS_FLOW_FRAGMENT_TAG

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomNavigation()
        if (savedInstanceState == null) {
            changeFlow(newsFCFragment, NEWS_FLOW_FRAGMENT_TAG)
        }
    }

    private fun setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.news -> changeFlow(newsFCFragment, NEWS_FLOW_FRAGMENT_TAG)
                R.id.favorites -> changeFlow(favoritesFCFragment, FAVORITES_FLOW_FRAGMENT_TAG)
            }
            true
        }

        bottomNavigationView.setOnNavigationItemReselectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.news -> newsFCFragment.resetStack()
                R.id.favorites -> favoritesFCFragment.resetStack()
            }
        }
    }

    private fun changeFlow(newFCFragment: FlowContainerFragment, tag: String) {
        val transaction = childFragmentManager.beginTransaction()
        childFragmentManager.fragments.filter {
            it != newFCFragment
        }.forEach {
            transaction.hide(it)
        }
        if (newFCFragment.isAdded) {
            transaction.show(newFCFragment)
        } else {
            transaction.add(R.id.flowContainer, newFCFragment, tag)
        }

        transaction.commit()
        currentlyShowingFragmentTag = tag
    }
}