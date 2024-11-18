package com.example.baseblackboard.ui.list

import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class BaseRecyclerViewHolder<B: ViewBinding, ITEM, LIS: BaseRecyclerListener<ITEM>> (
    protected val binding: B
) : RecyclerView.ViewHolder(binding.root) {

    var viewType: Int = ViewHolderValue.VIEW_HOLDER_TYPE_DATA
        private set

    fun setViewType(type: Int) {
        viewType = type
    }

    @CallSuper
    open fun recycle() {}
}