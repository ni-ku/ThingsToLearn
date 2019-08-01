package de.niku.braincards.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import de.niku.braincards.R
import kotlinx.android.synthetic.main.activity_main.view.*
import javax.inject.Inject

abstract class BaseActivity<D : ViewDataBinding, V : ViewModel> : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var daj: DispatchingAndroidInjector<Fragment>
    @Inject
    protected lateinit var mViewModel: V
    protected lateinit var mDataBinding: D
    protected lateinit var mToolbar: Toolbar
    protected lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mDataBinding = DataBindingUtil.setContentView(this, getLayoutResId())
        mDataBinding.setVariable(getViewBindingId(), mViewModel)
        mDataBinding.executePendingBindings()

        mToolbar = mDataBinding.root.toolbar
        mNavController = findNavController(R.id.nav_host_fragment)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return daj;
    }

    fun setupToolbar() {
        setSupportActionBar(mToolbar)
        setupActionBarWithNavController(mNavController)
        NavigationUI.setupWithNavController(mToolbar, mNavController, mDataBinding.root.drawer_layout)
    }

    override fun onSupportNavigateUp(): Boolean {
        return mNavController.navigateUp()
    }

    protected abstract fun getLayoutResId() : Int
    protected abstract fun getViewBindingId() : Int

}