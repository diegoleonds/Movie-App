package com.example.moviesapi.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesapi.R
/**
 * This activity is used as a container for the navigation graph
 */
class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        /**
         * this activity has an theme has a theme to make the screen all blue before it can load
         * the splash screen, after that, the theme is changed back to normal before onCreateMethod
         */
        setTheme(R.style.Theme_MoviesAPI)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
    }
}