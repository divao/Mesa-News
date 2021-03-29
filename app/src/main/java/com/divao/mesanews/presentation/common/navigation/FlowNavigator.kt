package com.divao.mesanews.presentation.common.navigation

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class FlowNavigator constructor(
    fragmentActivity: FragmentActivity,
    fm: FragmentManager,
    containerId: Int
) : SupportAppNavigator(fragmentActivity, fm, containerId) {

}