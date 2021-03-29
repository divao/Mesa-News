package com.divao.mesanews.presentation.common.navigation

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.divao.mesanews.ApplicationComponent
import com.divao.mesanews.presentation.common.FlowContainerFragment
import com.divao.mesanews.common.di.PerFlow
import com.divao.mesanews.data.repository.NewsRepository
import com.divao.mesanews.domain.datarepository.NewsDataRepository
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

@Module
class FlowModule(
    private val fragmentActivity: FragmentActivity,
    private val fm: FragmentManager,
    private val containerId: Int
) {
    @Provides
    @PerFlow
    fun provideCicerone(): Cicerone<Router> = Cicerone.create()

    @Provides
    @PerFlow
    fun provideNavigator(): FlowNavigator = FlowNavigator(fragmentActivity, fm, containerId)

    @Provides
    @PerFlow
    fun provideCustomRouter(cicerone: Cicerone<Router>): Router = cicerone.router

    @Provides
    @PerFlow
    fun newsDataRepository(newsRepository: NewsRepository): NewsDataRepository = newsRepository
}

@Component(dependencies = [(ApplicationComponent::class)], modules = [(FlowModule::class)])
@PerFlow
interface FlowComponent : ApplicationComponent {
    fun inject(flowContainerFragment: FlowContainerFragment)
    fun router(): Router
    fun newsDataRepository(): NewsDataRepository
}