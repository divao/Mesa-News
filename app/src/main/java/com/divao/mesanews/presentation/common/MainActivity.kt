package com.divao.mesanews.presentation.common

class MainActivity : SingleFragmentContainerActivity() {
    override val fragment = MainContentFragment.newInstance()
}