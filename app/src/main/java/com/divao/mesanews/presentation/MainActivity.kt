package com.divao.mesanews.presentation

import com.divao.mesanews.MainContentFragment
import com.divao.mesanews.SingleFragmentContainerActivity

class MainActivity : SingleFragmentContainerActivity() {
    override val fragment = MainContentFragment.newInstance()
}