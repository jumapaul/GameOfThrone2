package com.jerimkaura.got.util


import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import com.jerimkaura.got.R

class SuccessDialog {
    private var activity: Activity? = null
    private var dialog: AlertDialog? = null

    fun dialog(myActivity: Activity?) {
        activity = myActivity
    }

    @SuppressLint("InflateParams")
    fun startSuccessDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity!!.layoutInflater
        builder.setView(inflater.inflate(R.layout.success_dialog_layout, null))
        builder.setCancelable(true)
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun dismissSuccessDialog() {
        dialog!!.dismiss()
    }
}