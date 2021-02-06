package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val imm : InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val curFocus : View? = currentFocus

    if(curFocus!=null) {
        imm.hideSoftInputFromWindow(curFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

fun Activity.isKeyboardOpen() : Boolean {
    val rootView : View = findViewById(android.R.id.content)
    var rect = Rect()
    rootView.getWindowVisibleDisplayFrame(rect)

    val dm : DisplayMetrics = rootView.resources.displayMetrics
    val delta : Int = rootView.bottom - rect.bottom

    return delta > 128 * dm.density
}

fun Activity.isKeyboardClosed() : Boolean {
    return !isKeyboardOpen()
}