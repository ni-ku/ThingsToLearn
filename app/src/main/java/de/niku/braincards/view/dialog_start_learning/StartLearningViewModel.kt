package de.niku.braincards.view.dialog_start_learning

import androidx.lifecycle.MutableLiveData
import de.niku.braincards.Constants
import de.niku.braincards.R
import de.niku.braincards.common.base.BaseViewModel

class StartLearningViewModel : BaseViewModel<StartLearningEvents>() {

    val vdLearnMode: MutableLiveData<Int> = MutableLiveData()

    init {
        vdLearnMode.value = R.id.rb_mode_normal
    }

    fun done() {
        var resultData: StartLearningResultData
        when (vdLearnMode.value) {
            R.id.rb_mode_normal -> {
                resultData = StartLearningResultData(Constants.LEARN_MODE_NORMAL)
            }
            R.id.rb_mode_quiz -> {
                resultData = StartLearningResultData(Constants.LEARN_MODE_QUIZ)
            }
            else -> {
                resultData = StartLearningResultData(Constants.LEARN_MODE_NORMAL)
            }
        }
        mEvents.value = StartLearningEvents.Done(resultData)
    }

    fun cancel() {
        mEvents.value = StartLearningEvents.Cancel()
    }
}