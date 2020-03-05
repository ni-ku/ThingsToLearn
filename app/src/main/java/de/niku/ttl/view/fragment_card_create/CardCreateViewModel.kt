package de.niku.ttl.view.fragment_card_create

import androidx.lifecycle.MutableLiveData
import de.niku.ttl.R
import de.niku.ttl.common.base.BaseViewModel
import de.niku.ttl.model.Card

class CardCreateViewModel: BaseViewModel<CardCreateEvents>() {

    var frontValue: MutableLiveData<String> = MutableLiveData()
    var frontValueError: MutableLiveData<String> = MutableLiveData()
    var backValue: MutableLiveData<String> = MutableLiveData()
    var backValueError: MutableLiveData<String> = MutableLiveData()
    val isEditMode: MutableLiveData<Boolean> = MutableLiveData()
    val editPosition: MutableLiveData<Int> = MutableLiveData()

    init {
        isEditMode.value = false
    }

    fun initFromParams(position: Int, card: Card) {
        frontValue.value = card.front
        backValue.value = card.back
        editPosition.value = position
        isEditMode.value = true
    }

    fun done() {
        val frontVal = frontValue.value
        val backVal = backValue.value

        if (frontVal == null || frontVal.isEmpty()) {
            frontValueError.value = mResHelper?.getString(R.string.create_card_form_error_front_empty)
            return
        } else {
            frontValueError.value = ""
        }

        if (backVal == null || backVal.isEmpty()) {
            backValueError.value = mResHelper?.getString(R.string.create_card_form_error_back_empty)
            return
        } else {
            backValueError.value = ""
        }

        if (isEditMode.value == false) {
            var resultData = CardCreateResultData(
                frontValue.value!!,
                backValue.value!!
            )
            mEvents.value = CardCreateEvents.Done(resultData)
        } else {
            var resultData = CardEditResultData(
                editPosition.value!!,
                frontValue.value!!,
                backValue.value!!
            )
            mEvents.value = CardCreateEvents.EditDone(resultData)
        }
    }

    fun cancel() {
        mEvents.value = CardCreateEvents.Cancel()
    }
}