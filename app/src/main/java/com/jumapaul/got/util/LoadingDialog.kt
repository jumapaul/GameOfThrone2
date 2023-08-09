package com.jerimkaura.got.util


import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import com.jerimkaura.got.R

class LoadingDialog {
    private var activity: Activity? = null
    private var dialog: AlertDialog? = null

    fun dialog(myActivity: Activity?) {
        activity = myActivity
    }

    @SuppressLint("InflateParams")
    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity!!.layoutInflater
        builder.setView(inflater.inflate(R.layout.loading_dialog_layout, null))
        builder.setCancelable(true)
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun dismissLoadingDialog() {
        dialog!!.dismiss()
    }
}