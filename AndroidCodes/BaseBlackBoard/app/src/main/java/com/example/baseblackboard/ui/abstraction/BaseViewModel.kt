package com.example.baseblackboard.ui.abstraction

import androidx.lifecycle.AndroidViewModel
import com.example.baseblackboard.activity.AppApplication

open class BaseViewModel(private val app: AppApplication) : AndroidViewModel(app) {
}