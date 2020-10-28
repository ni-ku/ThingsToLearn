package de.niku.ttl.common.dialogs

import android.content.Context
import androidx.appcompat.app.AlertDialog

class InfoDialog(
    context: Context,
    dialogId: Int,
    title: Int,
    message: Int,
    textConfirm: Int,
    callback: InfoDialogResultReceiver
) {

    interface InfoDialogResultReceiver {
        fun onConfirm(dialogId: Int)
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
            .create()
        dialog!!.window
        dialog.show()
    }
}