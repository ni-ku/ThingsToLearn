package de.niku.ttl.view.activity_main

import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.navigation.NavigationView
import de.niku.ttl.BR
import de.niku.ttl.R
import de.niku.ttl.common.base.BaseActivity
import de.niku.ttl.common.navigation.NavigationResult
import de.niku.ttl.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(),
    NavController.OnDestinationChangedListener,
    NavigationView.OnNavigationItemSelectedListener {

    /**
     * Set of views where the navigation drawer
     * is accessible from
     */
    private val viewsWithDrawerMenu: Array<String> = arrayOf(
        "CardSets"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()
        AppBarConfiguration(mNavController.graph, mDataBinding.drawerLayout)
        mNavController.addOnDestinationChangedListener(this)
        mDataBinding.navigationView.setNavigationItemSelectedListener(this)
    }

    override fun getLayoutResId() = R.layout.activity_main
    override fun getViewBindingId() = BR.viewmodel

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

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        // whenever the view is changed lock/unlock the navigation drawer.
        // Navigation drawer should not be accessible from every view.
        if (viewsWithDrawerMenu.contains(destination.label)) {
            unlockNavDrawer()
        } else {
            lockNavDrawer()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        mDataBinding.drawerLayout.closeDrawer(GravityCompat.START)
        when (menuItem.itemId) {
            R.id.menu_settings -> {
                mNavController.navigate(R.id.settings_fragment)
                return true
            }
        }

        return false
    }

    fun lockNavDrawer() {
        mDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    fun unlockNavDrawer() {
        mDataBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

}
