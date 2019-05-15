package de.niku.braincards.view.dialog_start_learning

import android.os.Bundle
import androidx.lifecycle.Observer
import de.niku.braincards.BR
import de.niku.braincards.R
import de.niku.braincards.common.base.BaseDialog
import de.niku.braincards.common.resources.ResourceHelper
import de.niku.braincards.databinding.DialogStartLearningBinding

class StartLearningDialog(
    val resultReceiver: ResultReceiver
) : BaseDialog<DialogStartLearningBinding, StartLearningViewModel>() {

    interface ResultReceiver {
        fun onStartLearningDialogResult(result: StartLearningResultData)
    }

    companion object {
        val TAG: String = StartLearningDialog.toString()
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

    fun createLifecycleObservers() {
        mViewModel.mResHelper = ResourceHelper(context)
        lifecycle.addObserver(mViewModel.mResHelper)
    }

    fun connectObservables() {
        mViewModel.mEvents.observe(this, Observer { evt ->
            run {
                when(evt) {
                    is StartLearningEvents.Done -> {
                        resultReceiver?.onStartLearningDialogResult(evt.result)
                        dialog?.dismiss()
                    }
                    is StartLearningEvents.Cancel -> {
                        dialog?.dismiss()
                    }
                }
            }
        })

    }

    fun clearObservables() {
        mViewModel.mEvents.removeObservers(this)
    }
}