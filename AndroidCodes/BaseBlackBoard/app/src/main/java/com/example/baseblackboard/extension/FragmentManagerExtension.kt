package com.example.baseblackboard.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commitNow
import androidx.fragment.app.commit


fun FragmentManager.commit(
    now: Boolean,
    allowStateLoss: Boolean = false,
    body: FragmentTransaction.() -> Unit
) {
    if (now) commitNow(allowStateLoss, body) else commit(allowStateLoss, body)
}

fun Fragment.finishFragment() {
    activity?.runOnUiThread {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }
}