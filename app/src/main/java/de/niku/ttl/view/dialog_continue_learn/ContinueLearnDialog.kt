package de.niku.ttl.view.dialog_continue_learn

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import de.niku.ttl.R
import kotlinx.android.synthetic.main.dialog_continue_learn.view.*

class ContinueLearnDialog(
    private val resultReceiver: ResultReceiver
): DialogFragment() {

    interface ResultReceiver {
        fun onDone()
        fun onContinue()
    }

    companion object {
        val TAG: String = ContinueLearnDialog.toString()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view: View = LayoutInflater.from(context).inflate(R.layout.dialog_continue_learn, null)
        view.tv_text.setText(R.string.continue_lern_dialog_text)

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val alertDialog = builder
                .setTitle(requireContext().getString(R.string.play_again))
                .setView(view)
                .setPositiveButton(requireContext().getString(R.string.restart), null)
                .setNegativeButton(requireContext().getString(R.string.cancel), null)
                .setCancelable(false)
                .create()

            alertDialog.setCanceledOnTouchOutside(false)
            alertDialog.setOnKeyListener { _, code, _ ->
                run {
                    if (code == KeyEvent.KEYCODE_BACK) {
                        resultReceiver.onDone()
                        alertDialog.dismiss()
                        return@run false
                    }
                    return@run false
                }
            }

            alertDialog.setOnShowListener {
                val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                positiveButton.setOnClickListener {
                    resultReceiver.onContinue()
                    alertDialog.dismiss()
                }
                val negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                negativeButton.setOnClickListener {
                    resultReceiver.onDone()
                    alertDialog.dismiss()
                }
            }

            alertDialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}