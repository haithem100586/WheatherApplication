package com.android.weatherapplication.utils.extensions

import android.content.Context
import android.content.res.Resources
import android.widget.Toast
import androidx.annotation.DrawableRes

@DrawableRes
fun Context.resIdByName(resIdName: String?, resType: String): Int {
    resIdName?.let {
        return resources.getIdentifier(it, resType, packageName)
    }
    throw Resources.NotFoundException()
}

fun Context.displayToast(textToDisplay: Int): Toast {
    return Toast.makeText(this, textToDisplay, Toast.LENGTH_SHORT).apply { show() }
}
