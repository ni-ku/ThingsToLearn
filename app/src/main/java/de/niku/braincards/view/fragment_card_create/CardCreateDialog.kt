package de.niku.braincards.view.fragment_card_create

import android.os.Bundle
import androidx.lifecycle.Observer
import de.niku.braincards.BR
import de.niku.braincards.R
import de.niku.braincards.common.base.BaseDialog
import de.niku.braincards.common.resources.ResourceHelper
import de.niku.braincards.databinding.DialogCardCreateBinding

class CardCreateDialog(
    val resultReceiver: ResultReceiver,
    val editParams: CardEditParams?
) : BaseDialog<DialogCardCreateBinding, CardCreateViewModel>() {

    constructor(resultReceiver: ResultReceiver) : this(resultReceiver, null) {}

    companion object {
        val TAG: String = CardCreateDialog.toString()
    }

    override fun getLayoutResId(): Int {
        return R.layout.dialog_card_create
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
            mViewModel.initFromParams(editParams.position, editParams.card)
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
                    is CardCreateEvents.Done -> {
                        resultReceiver?.onCreateCard(evt.result)
                        dialog?.dismiss()
                    }
                    is CardCreateEvents.Cancel -> {
                        dialog?.dismiss()
                    }
                    is CardCreateEvents.EditDone -> {
                        resultReceiver?.onEditCard(evt.result)
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