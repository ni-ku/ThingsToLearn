package de.niku.ttl.view.fragment_card_set_learn

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import de.niku.ttl.BR
import de.niku.ttl.R
import de.niku.ttl.common.base.BaseFragment
import de.niku.ttl.databinding.FragmentCardSetLearnBinding
import de.niku.ttl.view.dialog_continue_learn.ContinueLearnDialog

class CardSetLearnFragment : BaseFragment<FragmentCardSetLearnBinding, CardSetLearnViewModel>(),
    GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener,
    ContinueLearnDialog.ResultReceiver {

    companion object {
        const val SWIPE_VELOCITY_THRESHOLD = 4000
        const val SWIPE_DISTANCE_THRESHOLD = 100
    }

    val args: CardSetLearnFragmentArgs by navArgs()
    private lateinit var mGestureDetector: GestureDetectorCompat

    override fun getLayoutResId(): Int = R.layout.fragment_card_set_learn
    override fun getViewBindingId(): Int = BR.viewmodel
    override fun performExtraViewBinding() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectObservables()

        view.setOnTouchListener { view, event ->
            run {
                if (mGestureDetector.onTouchEvent(event)) {
                    true
                } else {
                    return@run true
                }
            }
        }

        mGestureDetector = GestureDetectorCompat(context, this)
        mGestureDetector.setOnDoubleTapListener(this)

        val cardSetId = args.cardSetId
        val viceVersa = args.viceVersa
        val shuffle = args.shuffle
        mViewModel.initById(cardSetId, viceVersa, shuffle)
    }

    override fun onDestroy() {
        clearObservables()
        super.onDestroy()
    }

    fun connectObservables() {
        mViewModel.mEvents.observe(this, Observer { evt ->
            run {
                when (evt) {
                    is CardSetLearnEvents.CardSetDone -> {
                        showContinueLearnDialog()
                    }
                }
            }
        })
    }

    fun clearObservables() {
        mViewModel.mEvents.removeObservers(this)
    }

    fun showContinueLearnDialog() {
        val dialog = ContinueLearnDialog(this)
        dialog.show(fragmentManager!!, ContinueLearnDialog.TAG)
    }

    override fun onDone() {
        activity!!.onBackPressed()
    }

    override fun onContinue() {
        mViewModel.onRestart()
    }

    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return true
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }


    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        var distX: Float = e2!!.x - e1!!.x
        //Log.i("Fling Values", "vx: " + velocityX + ", vy: " + velocityY + "distx: " + distX)
        if (Math.abs(distX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
            mViewModel.onNextClick()
        }
        return true
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        mViewModel.onRevealCardClick()
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return true
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return true
    }
}
