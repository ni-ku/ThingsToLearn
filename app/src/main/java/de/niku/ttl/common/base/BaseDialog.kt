package de.niku.ttl.common.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseDialog<D: ViewDataBinding, V: ViewModel> : DialogFragment() {

    @Inject
    protected lateinit var mViewModel: V
    protected lateinit var mDataBinding: D

    protected abstract fun getLayoutResId() : Int
    protected abstract fun getViewBindingId() : Int
    protected abstract fun performExtraViewBinding()
    protected abstract fun getTitleMessage(): Int
    protected abstract fun getPositiveButtonMessage(): Int
    protected abstract fun getNegativeButtonMessage(): Int
    protected abstract fun onPositiveClick()
    protected abstract fun onNegativeClick()

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mDataBinding = DataBindingUtil.inflate(activity!!.layoutInflater, getLayoutResId(), null, false)
        mDataBinding.setVariable(getViewBindingId(), mViewModel)
        performExtraViewBinding()
        mDataBinding.lifecycleOwner = this
        mDataBinding.executePendingBindings()
        val view = mDataBinding.root

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val alertDialog = builder
                .setTitle(getTitleMessage())
                .setView(view)
                .setPositiveButton(getPositiveButtonMessage(), null)
                .setNegativeButton(getNegativeButtonMessage(), null)
                .create()

            alertDialog.setOnShowListener {
                val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                positiveButton.setOnClickListener {
                    onPositiveClick()
                }
                val negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                negativeButton.setOnClickListener {
                    onNegativeClick()
                }
            }

            alertDialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}