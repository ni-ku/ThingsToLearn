package de.niku.ttl.view.dialog_question_create

import android.os.Bundle
import androidx.lifecycle.Observer
import de.niku.ttl.BR
import de.niku.ttl.R
import de.niku.ttl.common.base.BaseDialog
import de.niku.ttl.common.resources.ResourceHelper
import de.niku.ttl.databinding.DialogQuestionCreateBinding

class QuestionCreateDialog(
    val resultReceiver: ResultReceiver,
    val editParams: QuestionEditParams?
) : BaseDialog<DialogQuestionCreateBinding, QuestionCreateViewModel>() {

    constructor(resultReceiver: ResultReceiver) : this(resultReceiver, null) {}

    override fun getLayoutResId(): Int {
        return R.layout.dialog_question_create
    }

    override fun getViewBindingId(): Int {
        return BR.viewmodel
    }

    override fun performExtraViewBinding() {
    }

    override fun getTitleMessage(): Int {
        return R.string.create_question_dialog_title
    }

    override fun getPositiveButtonMessage(): Int {
        return R.string.done
    }

    override fun getNegativeButtonMessage(): Int {
        return R.string.cancel
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

        if (editParams != null) {
            mViewModel.initFromParams(editParams.position, editParams.question)
        }
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
                    is QuestionCreateEvents.Done -> {
                        resultReceiver?.onCreateQuestion(evt.result)
                        dialog?.dismiss()
                    }
                    is QuestionCreateEvents.Cancel -> {
                       dialog?.dismiss()
                    }
                    is QuestionCreateEvents.EditDone -> {
                        resultReceiver?.onEditQuestion(evt.result)
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