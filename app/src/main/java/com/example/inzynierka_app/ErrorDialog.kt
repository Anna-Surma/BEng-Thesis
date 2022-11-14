package com.example.inzynierka_app

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

class ErrorDialog {
    fun createDialog(
        context: Context?,
        title: Int,
        message: Int,
        icon: Int
    ) {
        val builder = AlertDialog.Builder(context)
        with(builder) {
            setTitle(title)
            setMessage(message)
            builder.setIcon(icon)
            setPositiveButton("OK", DialogInterface.OnClickListener
            { dialog: DialogInterface, _: Int ->
                dialog.cancel()
            })
            show()
        }
    }

}