package de.niku.braincards.common.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<D : ViewDataBinding, V : ViewModel> : Fragment() {

    @Inject
    protected lateinit var mViewModel: V
    protected lateinit var mDataBinding: D

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mDataBinding = DataBindingUtil.inflate(layoutInflater, getLayoutResId(), container, false)
        mDataBinding.setVariable(getViewBindingId(), mViewModel)
        performExtraViewBinding()
        mDataBinding.lifecycleOwner = this
        mDataBinding.executePendingBindings()
        var view = mDataBinding.root
        return view
    }

    protected abstract fun getLayoutResId() : Int
    protected abstract fun getViewBindingId() : Int
    protected abstract fun performExtraViewBinding()

}