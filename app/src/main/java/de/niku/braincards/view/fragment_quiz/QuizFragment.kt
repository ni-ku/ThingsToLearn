package de.niku.braincards.view.fragment_quiz

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import de.niku.braincards.BR
import de.niku.braincards.R
import de.niku.braincards.common.base.BaseFragment
import de.niku.braincards.databinding.FragmentQuizBinding
import java.util.*

class QuizFragment : BaseFragment<FragmentQuizBinding, QuizViewModel>() {

    val args: QuizFragmentArgs by navArgs()
    val timer = Timer()
    val timerTask = object : TimerTask() {
        var cnt = 0

        override fun run() {
            cnt++
            if (cnt == 15) {
                this@QuizFragment.requireActivity().runOnUiThread {
                    mViewModel.onOptionSelected(QuizViewModel.OPTION_NONE)
                    this.cancel()
                }
            }

            var timeProgress = (100 / 14) * cnt
            mDataBinding.progressbarTimeLimit.progress = timeProgress
        }
    }
    val countDownTimer = object : CountDownTimer(15000, 1000) {
        override fun onFinish() {
            mViewModel.onOptionSelected(QuizViewModel.OPTION_NONE)
        }

        override fun onTick(millisUntilFinished: Long) {
            var timeProgress = ((100 / 14) * ((15000 - millisUntilFinished) / 1000)).toInt()
            mDataBinding.progressbarTimeLimit.progress = timeProgress
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_quiz
    override fun getViewBindingId(): Int = BR.viewmodel
    override fun performExtraViewBinding() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectObservables()

        val cardSetId = args.cardSetId
        mViewModel.initById(cardSetId)
    }

    override fun onDestroy() {
        clearObservables()
        super.onDestroy()
    }

    fun connectObservables() {
        mViewModel.mEvents.observe(this, Observer { evt ->
            run {
                when (evt) {
                    is QuizEvents.StopSelectionTimer -> {
                        countDownTimer.cancel()
                    }
                    is QuizEvents.StartSelectionTimer -> {
                        countDownTimer.start()
                    }
                }
            }
        })
    }

    fun clearObservables() {
        mViewModel.mEvents.removeObservers(this)
    }
}
