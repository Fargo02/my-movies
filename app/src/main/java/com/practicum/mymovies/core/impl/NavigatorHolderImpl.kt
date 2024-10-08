package com.practicum.mymovies.core.impl

import androidx.fragment.app.Fragment
import com.practicum.mymovies.core.navigation.Navigator
import com.practicum.mymovies.core.navigation.NavigatorHolder

class NavigatorHolderImpl : NavigatorHolder {

    private var navigator: Navigator? = null

    override fun attachNavigator(navigator: Navigator) {
        this.navigator = navigator
    }

    override fun detachNavigator() {
        this.navigator = null
    }

    override fun openFragment(fragment: Fragment) {
        navigator?.openFragment(fragment)
    }

}