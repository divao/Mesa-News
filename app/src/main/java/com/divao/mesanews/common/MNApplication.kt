package com.divao.mesanews.common

import android.app.Application
import com.divao.mesanews.ApplicationComponent
import com.divao.mesanews.ApplicationModule
import com.divao.mesanews.DaggerApplicationComponent
import com.evernote.android.state.StateSaver
import com.pacoworks.rxpaper2.RxPaperBook

class MNApplication : Application() {

    companion object {
        lateinit var daggerComponent: ApplicationComponent
    }

    val component: ApplicationComponent =
        DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()

    override fun onCreate() {
        super.onCreate()
        StateSaver.setEnabledForAllActivitiesAndSupportFragments(this, true)
        RxPaperBook.init(applicationContext)
    }
}