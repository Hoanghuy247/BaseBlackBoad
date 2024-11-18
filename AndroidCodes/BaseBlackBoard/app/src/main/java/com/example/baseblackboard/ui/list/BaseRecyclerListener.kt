package com.example.baseblackboard.ui.list

import android.view.View

interface BaseRecyclerListener<I> {

    fun itemClick(item: I) {}

    fun itemClick(position: Int, item: I) {}

    fun itemClick(position: Int, item: I, view: View) {}

    fun itemLongClick(position: Int, item: I) {}
}