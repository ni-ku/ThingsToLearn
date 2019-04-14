package de.niku.braincards.common.base

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity<D : ViewDataBinding, V : ViewModel> : FragmentActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var daj: DispatchingAndroidInjector<Fragment>
    @Inject
    protected lateinit var mViewModel: V
    protected lateinit var mDataBinding: D
    protected lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mDataBinding = DataBindingUtil.setContentView(this, getLayoutResId())
        mDataBinding.setVariable(getViewBindingId(), mViewModel)
        mDataBinding.executePendingBindings()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return daj;
    }

    fun setupToolbar() {
    }

    protected abstract fun getLayoutResId() : Int
    protected abstract fun getViewBindingId() : Int

}