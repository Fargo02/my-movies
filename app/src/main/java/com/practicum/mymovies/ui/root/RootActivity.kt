package com.practicum.mymovies.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.practicum.mymovies.R
import com.practicum.mymovies.core.impl.NavigatorImpl
import com.practicum.mymovies.core.navigation.NavigatorHolder
import com.practicum.mymovies.databinding.ActivityRootBinding
import com.practicum.mymovies.ui.movies.MoviesFragment
import org.koin.android.ext.android.inject

class RootActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRootBinding

    private val navigatorHolder: NavigatorHolder by inject()

    private val navigator = NavigatorImpl(
        fragmentContainerViewId = R.id.rootFragmentContainerView,
        fragmentManager = supportFragmentManager
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            navigator.openFragment(
                MoviesFragment()
            )
        }

    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.attachNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.detachNavigator()
    }
}