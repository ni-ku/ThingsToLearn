package de.niku.ttl.common.dialogs

import android.app.AlertDialog
import android.content.Context

class DecisionDialog(
    context: Context,
    dialogId: Int,
    title: Int,
    message: Int,
    textConfirm: Int,
    textCancel: Int,
    callback: DecisionDialogResultReceiver
) {

    interface DecisionDialogResultReceiver {
        fun onConfirm(dialogId: Int)
        fun onCancel(dialogId: Int)
    }

    init {
        val dialog: AlertDialog? = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(textConfirm) { _, which ->
                run {
                    callback.onConfirm(which)
                }
            }
            .setNegativeButton(textCancel) { _, which ->
                run {
                    callback.onCancel(which)
                }
            }
            .create()
        dialog!!.window
        dialog.show()
    }
}