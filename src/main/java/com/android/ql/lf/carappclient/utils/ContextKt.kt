package com.android.ql.lf.carappclient.utils

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.IBinder
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.android.ql.lf.carappclient.R

/**
 * Created by lf on 18.2.10.
 * @author lf on 18.2.10
 */
fun Fragment.toast(message: String) = this.context.toast(message)

fun Context.toast(message: String) {
    val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    toast.view.setBackgroundResource(R.drawable.shape_bt_bg1)
    toast.view.findViewById<TextView>(android.R.id.message).setTextColor(Color.WHITE)
    toast.show()
}

fun Context.hiddenKeyBoard(token: IBinder) {
    val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
}


fun Fragment.startPhone(phone: String) {
    val builder = AlertDialog.Builder(this.context)
    builder.setMessage("是否拨打电话？")
    builder.setNegativeButton("否", null)
    builder.setPositiveButton("是") { _, _ ->
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
    builder.create().show()
}

fun Fragment.alert(title: String? = "title",
                   message: String = "message",
                   positiveButton: String = "是",
                   negativeButton: String = "否",
                   positiveAction: ((dialog: DialogInterface, which: Int) -> Unit)? = null,
                   negativeAction: ((dialog: DialogInterface, which: Int) -> Unit)? = null) =
        this.context.alert(title, message, positiveButton, negativeButton, positiveAction, negativeAction)

fun Fragment.alert(message: String = "message",
                   positiveButton: String = "是",
                   negativeButton: String = "否", positiveAction: ((dialog: DialogInterface, which: Int) -> Unit)? = null) =
        this.alert(null, message, positiveButton, negativeButton, positiveAction, null)

fun Context.alert(title: String? = "title",
                  message: String = "message",
                  positiveButton: String = "是",
                  negativeButton: String = "否",
                  positiveAction: ((dialog: DialogInterface, which: Int) -> Unit)? = null,
                  negativeAction: ((dialog: DialogInterface, which: Int) -> Unit)? = null) {
    val builder = AlertDialog.Builder(this)
    builder.setMessage(message)
    builder.setTitle(title)
    builder.setNegativeButton(negativeButton, negativeAction)
    builder.setPositiveButton(positiveButton, positiveAction)
    builder.create().show()
}