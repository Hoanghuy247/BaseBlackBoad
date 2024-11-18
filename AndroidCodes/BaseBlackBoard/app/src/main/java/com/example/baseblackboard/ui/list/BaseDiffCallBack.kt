package com.example.baseblackboard.ui.list

import androidx.recyclerview.widget.DiffUtil

class BaseDiffCallBack<T>: DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
        return false
    }
}