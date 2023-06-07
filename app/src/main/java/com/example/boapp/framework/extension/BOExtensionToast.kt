package com.example.boapp.framework.extension

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.example.boapp.R

fun Toast.showToastSuccess(message: String, activity: Activity, time: Long = 2500) {

    val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(activity, R.style.AlertDialogRoundBase)
    val layoutView: View? = activity.layoutInflater.inflate(R.layout.custom_toast_success, null)
    val ivClose: AppCompatImageView? = layoutView?.findViewById(R.id.ivClose)
    val tvMessage: AppCompatTextView? = layoutView?.findViewById(R.id.tvMessage)
    dialogBuilder.setView(layoutView)

    val alertDialog: AlertDialog = dialogBuilder.create()
    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alertDialog.window?.setGravity(Gravity.TOP)
    alertDialog.show()

    tvMessage?.text = message
    ivClose?.setOnClickListener { alertDialog.dismiss() }

    Handler(Looper.getMainLooper()).postDelayed({
        try {
            alertDialog.dismiss()
        } catch (_: Exception) { }
    }, time)
}

fun Toast.showToastFailed(message: String, activity: Activity, time: Long = 5000) {

    val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(activity, R.style.AlertDialogRoundBase)
    val layoutView: View? = activity.layoutInflater.inflate(R.layout.custom_toast_failed, null)
    val ivClose: AppCompatImageView? = layoutView?.findViewById(R.id.ivClose)
    val tvMessage: AppCompatTextView? = layoutView?.findViewById(R.id.tvMessage)
    dialogBuilder.setView(layoutView)

    val alertDialog: AlertDialog = dialogBuilder.create()
    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alertDialog.window?.setGravity(Gravity.TOP)
    alertDialog.show()

    tvMessage?.text = message
    ivClose?.setOnClickListener { alertDialog.dismiss() }

    Handler(Looper.getMainLooper()).postDelayed({
        try {
            if (!activity.isDestroyed) alertDialog.dismiss()
        } catch (_: Exception) { }
    }, time)

}

fun Toast.showToastInfo(message: String, activity: Activity, time: Long = 2500) {

    val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(activity, R.style.AlertDialogRoundBase)
    val layoutView: View? = activity.layoutInflater.inflate(R.layout.custom_toast_info, null)
    val ivClose: AppCompatImageView? = layoutView?.findViewById(R.id.ivClose)
    val tvMessage: AppCompatTextView? = layoutView?.findViewById(R.id.tvMessage)
    dialogBuilder.setView(layoutView)

    val alertDialog: AlertDialog = dialogBuilder.create()
    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alertDialog.window?.setGravity(Gravity.TOP)
    alertDialog.show()

    tvMessage?.text = message
    ivClose?.setOnClickListener { alertDialog.dismiss() }

    Handler(Looper.getMainLooper()).postDelayed({
        try {
            alertDialog.dismiss()
        } catch (_: Exception) { }
    }, time)
}
