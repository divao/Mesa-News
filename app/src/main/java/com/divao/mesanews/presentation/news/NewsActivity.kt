package com.divao.mesanews.presentation.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.divao.mesanews.R

class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_fragment_container)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerFrameLayout, NewsFragment.newInstance())
            .commit()

    }

}