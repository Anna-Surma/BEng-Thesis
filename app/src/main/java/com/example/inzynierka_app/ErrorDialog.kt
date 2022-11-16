package com.example.inzynierka_app

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

class ErrorDialog {
    fun createDialog(
        context: Context?,
        title: Int,
        message: Int,
        icon: Int,
        setBoth: Boolean,
    ) {
        val builder = AlertDialog.Builder(context)
        with(builder) {
            setTitle(title)
            setMessage(message)
            builder.setIcon(icon)
            if(setBoth){
                setPositiveButton("Delete", DialogInterface.OnClickListener
                { dialog: DialogInterface, _: Int ->
                    dialog.cancel()
                })
                setNegativeButton("Cancel", DialogInterface.OnClickListener
                { dialog: DialogInterface, _: Int ->
                    dialog.cancel()
                })
            }
            else{
                setPositiveButton("OK", DialogInterface.OnClickListener
                { dialog: DialogInterface, _: Int ->
                    dialog.cancel()
                })
            }
            show()
        }
    }

}