package com.example.baseblackboard.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.baseblackboard.utils.lgd
import com.example.baseblackboard.utils.lge

abstract class BaseAdapter<ITEM,
        DATA: BaseData<ITEM>,
        LIS: BaseRecyclerListener<ITEM>,
        VH: BaseRecyclerViewHolder<ViewBinding, ITEM, LIS>>(): RecyclerView.Adapter<VH>() {

    protected var data: DATA? = null
        private set
    protected var listener: LIS? = null
        private set
    private var isDestroy = false

    private val onDataChangeListener = object : BaseData.OnDataChangeListener {
        override fun onDataChange() {
            handleDataChangeListener()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return createViewHolderInternal(parent, viewType)
    }

    abstract fun createViewHolderInternal(parent: ViewGroup, viewType: Int): VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        try {
            getItemPosition(position)?.let { bindViewHolder(holder, position, it, listener) }
        } catch (e: Exception) {
            lge("onBindViewHolder: error $position -$e")
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        try {
            getItemPosition(position)?.let { bindViewHolder(holder, position, it, listener, payloads) }
        } catch (e: Exception) {
            lge("onBindViewHolder: error $position - $e")
        }
    }

    abstract fun bindViewHolder(holder: VH, position: Int, item: ITEM, listener: LIS?)

    open fun bindViewHolder(holder: VH, position: Int, item: ITEM, listener: LIS?, payloads: MutableList<Any>) {}

    open fun getItemPosition(position: Int): ITEM? {
        try {
            return data?.getItemAtPosition(position)
        } catch (e: Exception) {
            lge("getItemPosition: ${e.message}")
            return null
        }
    }

    open fun setData(data: DATA) {
        this.data?.removeOnDataChangeListener(onDataChangeListener)
        this.data = data
        this.data?.addOnDataChangeListener(onDataChangeListener)
    }

    fun setListen(listener: LIS?, update: Boolean = true) {
        this.listener = listener
        if (update) {
            notifyDataSetChanged()
        }
    }

    open fun onDestroy() {
        this.data?.removeOnDataChangeListener(onDataChangeListener)
        listener = null
        isDestroy = true
    }

    protected open fun handleDataChangeListener() {
        lgd("${this@BaseAdapter}: onDataChangeListener = ${data?.getSize()}")
        notifyDataChange()
    }

    open fun notifyDataChange() {
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data?.getItemCount() ?: 0
    }

    protected open fun getCurrentList(): List<ITEM>? {
        return data?.currentList
    }
}