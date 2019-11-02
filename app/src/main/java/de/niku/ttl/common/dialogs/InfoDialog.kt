package de.niku.ttl.common.dialogs

import android.content.Context
import android.view.Window
import androidx.appcompat.app.AlertDialog

class InfoDialog {

    interface InfoDialogResultReceiver {
        fun onConfirm(dialogId: Int)
    }

    constructor(
        context: Context,
        dialogId: Int,
        title: Int,
        message: Int,
        textConfirm: Int,
        callback: InfoDialogResultReceiver
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
            .create()

        var window: Window = dialog!!.window
        if (window != null) {
            dialog.show()
        }
    }
}