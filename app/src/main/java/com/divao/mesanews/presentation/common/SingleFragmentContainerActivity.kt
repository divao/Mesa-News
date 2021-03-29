package com.divao.mesanews.presentation.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.divao.mesanews.R

abstract class SingleFragmentContainerActivity : AppCompatActivity() {

    abstract val fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.single_fragment_container)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerFrameLayout, fragment)
                .commit()
        }
    }

    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(R.id.fragmentContainerFrameLayout) as? ExitHandler)?.onBackPressed()
    }
}