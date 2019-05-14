package de.niku.braincards.view.activity_main

import android.os.Bundle
import android.view.Menu
import androidx.fragment.app.FragmentManager
import de.niku.braincards.BR
import de.niku.braincards.R
import de.niku.braincards.common.base.BaseActivity
import de.niku.braincards.common.navigation.NavigationResult
import de.niku.braincards.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()
    }

    override fun getLayoutResId() = R.layout.activity_main
    override fun getViewBindingId() = BR.viewmodel

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    /**
     *  Hand back a Bundle as result to previous Fragment
     *  as the Jetpack Navigation Component doesn't have something like
     *  startFragmentForResult like we would have for activities
     *
     *  https://medium.com/google-developer-experts/using-navigation-architecture-component-in-a-large-banking-app-ac84936a42c2
     */
    fun navigateBackWithResult(result: Bundle) {
        val childFragmentManager = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.childFragmentManager
        var backStackListener: FragmentManager.OnBackStackChangedListener by Delegates.notNull()
        backStackListener = FragmentManager.OnBackStackChangedListener {
            (childFragmentManager?.fragments?.get(0) as NavigationResult).onNavigationResult(result)
            childFragmentManager.removeOnBackStackChangedListener(backStackListener)
        }
        childFragmentManager?.addOnBackStackChangedListener(backStackListener)
        mNavController.popBackStack()
    }

}
