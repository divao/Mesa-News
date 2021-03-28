package com.divao.mesanews

import android.app.Application
import com.evernote.android.state.StateSaver

class MNApplication : Application() {

    companion object {
        lateinit var daggerComponent: ApplicationComponent
    }

    val component: ApplicationComponent =
        DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()

    override fun onCreate() {
        super.onCreate()
        StateSaver.setEnabledForAllActivitiesAndSupportFragments(this, true)
    }
}