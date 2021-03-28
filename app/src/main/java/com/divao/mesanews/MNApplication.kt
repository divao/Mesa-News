package com.divao.mesanews

import android.app.Application

class MNApplication : Application() {
    val component: ApplicationComponent =
        DaggerApplicationComponent.builder().applicationModule(ApplicationModule()).build()
}