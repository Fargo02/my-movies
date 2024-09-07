package com.practicum.mymovies.core.impl

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.practicum.mymovies.core.navigation.Navigator

class NavigatorImpl(
    override val fragmentContainerViewId: Int,
    override val fragmentManager: FragmentManager,
) : Navigator {

    override fun openFragment(fragment: Fragment) {
        fragmentManager.commit {
            replace(fragmentContainerViewId, fragment)
            addToBackStack(null)
        }
    }

}