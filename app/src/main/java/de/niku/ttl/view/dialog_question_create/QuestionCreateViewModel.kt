package de.niku.ttl.view.dialog_question_create

import androidx.lifecycle.MutableLiveData
import de.niku.ttl.R
import de.niku.ttl.common.base.BaseViewModel
import de.niku.ttl.model.Question

class QuestionCreateViewModel : BaseViewModel<QuestionCreateEvents>() {

    var vdQuestionText: MutableLiveData<String> = MutableLiveData()
    var vdQuestionError: MutableLiveData<String> = MutableLiveData()

    private val isEditMode: MutableLiveData<Boolean> = MutableLiveData()
    private val editPosition: MutableLiveData<Int> = MutableLiveData()

    init {
        isEditMode.value = false
        vdQuestionText.value = ""
    }

    fun initFromParams(position: Int, question: Question) {
        vdQuestionText.value = question.text
        editPosition.value = position
        isEditMode.value = true
    }

    fun onAddFrontPlaceholder() {
        vdQuestionText.value = vdQuestionText.value.plus(Question.QUESTION_FRONT_PLACEHOLDER)
        mEvents.value = QuestionCreateEvents.SetCursorToPosition
    }

    fun onAddBackPlaceholder() {
        vdQuestionText.value = vdQuestionText.value.plus(Question.QUESTION_BACK_PLACEHOLDER)
        mEvents.value = QuestionCreateEvents.SetCursorToPosition
    }

    fun done() {
        val questionText = vdQuestionText.value
        if (questionText == null || questionText.isEmpty()) {
            vdQuestionError.value = mResHelper.getString(R.string.create_question_form_error_question_empty)
            return
        } else {
            vdQuestionError.value = ""
        }

        val sideIdents = listOf(Question.QUESTION_FRONT_PLACEHOLDER, Question.QUESTION_BACK_PLACEHOLDER)
        val index: Pair<Int, String>? = questionText.findAnyOf(sideIdents, 0, true)
        if (index == null) {
            vdQuestionError.value  = mResHelper.getString(R.string.create_question_form_error_question_include_value)
            return
        } else {
            vdQuestionError.value = ""
        }

        if (isEditMode.value == false) {
            val result = QuestionCreateResult(
                vdQuestionText.value!!
            )
            mEvents.value = QuestionCreateEvents.Done(result)
        } else {
            val result = QuestionEditParams(
                editPosition.value!!,
                Question(null, vdQuestionText.value!!)
            )
            mEvents.value = QuestionCreateEvents.EditDone(result)
        }
    }

    fun cancel() {
        mEvents.value = QuestionCreateEvents.Cancel
    }
}
