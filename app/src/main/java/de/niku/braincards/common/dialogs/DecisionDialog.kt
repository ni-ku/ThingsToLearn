package de.niku.braincards.common.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.Window

class DecisionDialog {

    interface DecisionDialogResultReceiver {
        fun onConfirm(dialogId: Int)
        fun onCancel(dialogId: Int)
    }

    constructor(
        context: Context,
        dialogId: Int,
        title: Int,
        message: Int,
        textConfirm: Int,
        textCancel: Int,
        callback: DecisionDialogResultReceiver
    ) {

        var dialog: AlertDialog? = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(textConfirm) { dialog, which ->
                run {
                    if (callback != null) {
                        callback.onConfirm(which)
                    }
                }
            }
            .setNegativeButton(textCancel) { dialog, which ->
                run {
                    if (callback != null) {
                        callback.onCancel(which)
                    }
                }
            }
            .create()

        var window: Window = dialog!!.window
        if (window != null) {
            dialog.show()
        }
    }
}