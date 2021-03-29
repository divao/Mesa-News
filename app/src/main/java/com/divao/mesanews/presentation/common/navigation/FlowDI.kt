package com.divao.mesanews.cicerone

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.divao.mesanews.ApplicationComponent
import com.divao.mesanews.FlowContainerFragment
import com.divao.mesanews.di.PerFlow
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
}

@Component(dependencies = [(ApplicationComponent::class)], modules = [(FlowModule::class)])
@PerFlow
interface FlowComponent : ApplicationComponent {
    fun inject(flowContainerFragment: FlowContainerFragment)
    fun router(): Router
}