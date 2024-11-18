package com.example.baseblackboard.ui.list

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.example.baseblackboard.utils.lgd
import com.example.baseblackboard.utils.lge
import java.lang.Exception

abstract class BaseListAdapter<
        ITEM,
        DATA : BaseData<ITEM>,
        LIS : BaseRecyclerListener<ITEM>,
        VH : BaseRecyclerViewHolder<ViewBinding, ITEM, LIS>>
    (diff: BaseDiffCallBack<ITEM> = BaseDiffCallBack()) : ListAdapter<ITEM, VH>(diff) {

    protected var data: DATA? = null
        private set
    var listener: LIS? = null
        private set

    private var isDestroy = false
    private var onDataChangeListener: BaseData.OnDataChangeListener? = object : BaseData.OnDataChangeListener {
        override fun onDataChange() {
            if (isDestroy) {
                return
            }
            handleDataChangeListener()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return createViewHolderInternal(parent, viewType)
    }

    abstract fun createViewHolderInternal(parent: ViewGroup, viewType: Int): VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        try {
            data?.getItemAtPosition(position)?.let { bindViewHolder(holder, position, it, listener) }
        } catch (e: Exception) {
            lge("onBindViewHolder: error $position - $e")
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        try {
            data?.getItemAtPosition(position)?.let { bindViewHolder(holder, position, it, listener, payloads) }
        } catch (e: Exception) {
            lge("onBindViewHolder: error $position - $e")
        }
    }

    abstract fun bindViewHolder(holder: VH, position: Int, item: ITEM, listener: LIS?)

    open fun bindViewHolder(holder: VH, position: Int, item: ITEM, listener: LIS?, payloads: MutableList<Any>) {}

    protected open fun handleDataChangeListener() {
        lgd("${this@BaseListAdapter}: onDataChangeListener = ${data?.getSize()}")
        submitList(data?.currentList)
    }

    open fun setData(data: DATA) {
        this.data?.removeOnDataChangeListener(onDataChangeListener!!)
        this.data = data
        this.data?.addOnDataChangeListener(onDataChangeListener!!)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListen(listener: LIS) {
        if (this.listener != listener) {
            this.listener = listener
            notifyDataSetChanged()
        }
    }

    fun reSubmitList() {
        handleDataChangeListener()
    }

    open fun onDestroy() {
        isDestroy = false
        if (onDataChangeListener != null) {
            this.data?.removeOnDataChangeListener(onDataChangeListener!!)
        }
        onDataChangeListener = null
        listener = null
    }

    fun checkIfEmpty(): Boolean {
        return itemCount == 0
    }
}