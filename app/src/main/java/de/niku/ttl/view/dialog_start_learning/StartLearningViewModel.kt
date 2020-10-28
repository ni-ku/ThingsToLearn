package de.niku.ttl.view.dialog_start_learning

import androidx.lifecycle.MutableLiveData
import de.niku.ttl.Constants
import de.niku.ttl.R
import de.niku.ttl.common.base.BaseViewModel
import de.niku.ttl.view.dialog_start_learning.StartLearningResultData as StartLearningResultData1

class StartLearningViewModel : BaseViewModel<StartLearningEvents>() {

    val vdLearnMode: MutableLiveData<Int> = MutableLiveData()

    init {
        vdLearnMode.value = R.id.rb_mode_normal
    }

    fun done() {
        val resultData: StartLearningResultData1 = when (vdLearnMode.value) {
            R.id.rb_mode_normal -> {
                StartLearningResultData1(Constants.LEARN_MODE_NORMAL)
            }
            R.id.rb_mode_quiz -> {
                StartLearningResultData1(Constants.LEARN_MODE_QUIZ)
            }
            else -> {
                StartLearningResultData1(Constants.LEARN_MODE_NORMAL)
            }
        }
        mEvents.value = StartLearningEvents.Done(resultData)
    }

    fun cancel() {
        mEvents.value = StartLearningEvents.Cancel
    }
}