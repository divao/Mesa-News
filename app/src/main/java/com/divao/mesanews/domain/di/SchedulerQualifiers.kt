package com.divao.mesanews.domain.di

import javax.inject.Qualifier

@Qualifier
annotation class MainScheduler

@Qualifier
annotation class BackgroundScheduler