package com.divao.mesanews.presentation.common.navigation

import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.divao.mesanews.presentation.scene.news.NewsFragment
import kotlinx.android.parcel.Parcelize
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class MNScreen : SupportAppScreen(), Parcelable {
    @Parcelize
    class NewsScreen : MNScreen() {
        override fun getFragment(): Fragment = NewsFragment.newInstance(false)
    }

    @Parcelize
    class FavoriteNewsScreen : MNScreen() {
        override fun getFragment(): Fragment = NewsFragment.newInstance(true)
    }
}