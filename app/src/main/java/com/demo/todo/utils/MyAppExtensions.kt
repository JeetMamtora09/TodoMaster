package com.demo.todo.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.demo.todo.R
import com.google.android.material.snackbar.Snackbar

val Activity.contentView: View
    get() = findViewById<ViewGroup>(android.R.id.content).getChildAt(0)

fun Activity.snack(message: String, duration: Int = Snackbar.LENGTH_LONG) {
    contentView.run {
        Snackbar.make(this, message, duration).show()
    }
}

fun View.snack(message: String, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, message, duration).show()
}


fun Activity.showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

fun TextView.hideKeyboard() {
    val inputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun Context.showTwoButtonAlertDialog(
    title: String = getString(R.string.app_name),
    message: String,
    callBack: () -> Unit
) {
    val builder = AlertDialog.Builder(this)

    with(builder)
    {
        setTitle(title)
        setMessage(message)
        setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.dismiss()
            callBack.invoke()
        }
        setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.dismiss()
        }
        show()
    }
}

fun Activity.showTwoButtonAlertDialog(
    title: String = getString(R.string.app_name),
    message: String,
    callBack: () -> Unit
) {
    val builder = AlertDialog.Builder(this)

    with(builder)
    {
        setTitle(title)
        setMessage(message)
        setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.dismiss()
            callBack.invoke()
        }
        setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.dismiss()
        }
        show()
    }
}
