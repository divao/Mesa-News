package com.divao.mesanews.presentation.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.divao.mesanews.R
import com.divao.mesanews.common.MNApplication
import com.divao.mesanews.presentation.common.navigation.*
import com.divao.mesanews.presentation.common.navigation.FlowComponent
import com.divao.mesanews.presentation.common.navigation.FlowModule
import com.divao.mesanews.presentation.common.navigation.FlowNavigator
import com.divao.mesanews.presentation.common.navigation.MNScreen
import com.evernote.android.state.State
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import java.lang.RuntimeException
import javax.inject.Inject

class FlowContainerFragment : Fragment(), ExitHandler {

    companion object {
        fun newInstance(initialScreen: MNScreen) = FlowContainerFragment().apply {
            this.initialScreen = initialScreen
        }
    }

    @State
    lateinit var initialScreen: MNScreen

    val component: FlowComponent by lazy {
        activity?.let {
            DaggerFlowComponent.builder()
                .applicationComponent((it.application as MNApplication).component).flowModule(
                    FlowModule(it, childFragmentManager, R.id.fragmentContainerFrameLayout)
                ).build()
        } ?: throw RuntimeException("FlowContainerFragmetn not attached to an Activity yet")
    }

    @Inject
    lateinit var cicerone: Cicerone<Router>

    @Inject
    lateinit var navigator: FlowNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.single_fragment_container, container, false)
    }

    override fun onResume() {
        super.onResume()
        cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        cicerone.navigatorHolder.removeNavigator()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            cicerone.router.replaceScreen(initialScreen)
        }
    }

    override fun onBackPressed() {
        cicerone.router.exit()
    }

    fun resetStack() {
        cicerone.router.backTo(initialScreen)
    }
}