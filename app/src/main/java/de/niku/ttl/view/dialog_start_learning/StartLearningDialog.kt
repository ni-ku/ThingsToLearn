package de.niku.ttl.view.dialog_start_learning

import android.os.Bundle
import androidx.lifecycle.Observer
import de.niku.ttl.BR
import de.niku.ttl.R
import de.niku.ttl.common.base.BaseDialog
import de.niku.ttl.common.resources.ResourceHelper
import de.niku.ttl.databinding.DialogStartLearningBinding

class StartLearningDialog(
    private val resultReceiver: ResultReceiver
) : BaseDialog<DialogStartLearningBinding, StartLearningViewModel>() {

    interface ResultReceiver {
        fun onStartLearningDialogResult(result: StartLearningResultData)
    }

    override fun getLayoutResId(): Int = R.layout.dialog_start_learning
    override fun getViewBindingId(): Int = BR.viewmodel
    override fun getTitleMessage(): Int = R.string.start_lerning
    override fun getPositiveButtonMessage(): Int = R.string.start
    override fun getNegativeButtonMessage(): Int = R.string.cancel

    override fun performExtraViewBinding() {
    }

    override fun onPositiveClick() {
        mViewModel.done()
    }

    override fun onNegativeClick() {
        mViewModel.cancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectObservables()
        createLifecycleObservers()
    }

    override fun dismiss() {
        clearObservables()
        super.dismiss()
    }

    private fun createLifecycleObservers() {
        mViewModel.mResHelper = ResourceHelper(context)
        lifecycle.addObserver(mViewModel.mResHelper)
    }

    private fun connectObservables() {
        mViewModel.mEvents.observe(this, Observer { evt ->
            run {
                when(evt) {
                    is StartLearningEvents.Done -> {
                        resultReceiver.onStartLearningDialogResult(evt.result)
                        dialog?.dismiss()
                    }
                    is StartLearningEvents.Cancel -> {
                        dialog?.dismiss()
                    }
                }
            }
        })
    }

    private fun clearObservables() {
        mViewModel.mEvents.removeObservers(this)
    }
}