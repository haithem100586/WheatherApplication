package com.helloumi.weatherapplication.utils.extensions

import android.content.Context
import android.content.res.Resources
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

@DrawableRes
fun Context.resIdByName(resIdName: String?, resType: String): Int {
    resIdName?.let {
        return resources.getIdentifier(it, resType, packageName)
    }
    throw Resources.NotFoundException()
}

fun Context.displayToast(@StringRes textToDisplay: Int): Toast {
    return  Toast.makeText(this, getString(textToDisplay), Toast.LENGTH_SHORT).apply { show() }
}
