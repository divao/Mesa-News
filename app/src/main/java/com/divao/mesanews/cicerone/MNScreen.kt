package com.divao.mesanews.cicerone

import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.divao.mesanews.presentation.news.NewsFragment
import kotlinx.android.parcel.Parcelize
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class MNScreen : SupportAppScreen(), Parcelable {
    @Parcelize
    class NewsScreen : MNScreen() {
        override fun getFragment(): Fragment = NewsFragment.newInstance()
    }
}