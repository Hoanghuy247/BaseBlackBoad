package com.example.baseblackboard.ui.list

import com.example.baseblackboard.utils.ThreadUtils
import com.example.baseblackboard.utils.lge
import kotlin.Exception

open class BaseData<ITEM> {

    protected var _currentList = mutableListOf<ITEM>()
    val currentList: List<ITEM> get() = _currentList

    private val LOCK = "LOCK"
    private val listenerList: ArrayList<OnDataChangeListener> = ArrayList()
    private var initData = false

    open fun setData(data: List<ITEM>, force: Boolean = false) {
        synchronized(LOCK) {
            if (!initData) {
                initData = true
            }
            if (force || !compareData(data, this.currentList)) {
                _currentList = ArrayList(data.toList())
                notifyDataChange()
            }
        }
    }

    open fun setDataNotNotify(data: List<ITEM>) {
        synchronized(LOCK) {
            if (!initData) {
                initData = true
            }
            _currentList = ArrayList(data.toList())
        }
    }

    open fun deleteItem(item: ITEM, notify: Boolean = false) {
        synchronized(LOCK) {
            if (currentList.contains(item)) {
                _currentList.remove(item)
                if (notify) {
                    rebaseData()
                }
            }
        }
    }

    open fun addItem(item: ITEM, notify: Boolean = false) {
        synchronized(LOCK) {
            if (!currentList.contains(item)) {
                _currentList.add(item)
                if (notify) {
                    rebaseData()
                }
            }
        }
    }

    open fun rebaseData() {
        setData(ArrayList(currentList.toList()), true)
    }

    open fun getSize(): Int {
        return currentList.size
    }

    open fun getItemAtPosition(position: Int): ITEM? {
        return try {
            currentList[position]
        } catch (e: Exception) {
            lge("getItemAtPosition: $position - $e")
            null
        }
    }

    open fun getPositionByItem(item: ITEM): Int? {
        return try {
            currentList.indexOf(item)
        } catch (e: Exception) {
            lge("getPositionByItem: ${e.message}")
            null
        }
    }

    private fun compareData(new: List<ITEM>, old: List<ITEM>): Boolean {
        if (new.size != old.size) {
            return false
        }
        new.forEachIndexed { index, item ->
            if (!areContentsTheSame(old[index], new[index]) || areItemTheSame(old[index], new[index])) {
                return false
            }
        }
        return true
    }

    protected open fun areItemTheSame(old: ITEM, new: ITEM): Boolean {
        return false
    }

    protected open fun areContentsTheSame(old: ITEM, new: ITEM): Boolean {
        return false
    }

    fun addOnDataChangeListener(listener: OnDataChangeListener, isRunNow: Boolean = true) {
        if (listenerList.contains(listener)) {
            return
        }
        listenerList.add(listener)
        if (isRunNow && initData) {
            listener.onDataChange()
        }
    }

    fun removeOnDataChangeListener(listener: OnDataChangeListener) {
        listenerList.remove(listener)
    }

    protected fun notifyDataChange() {
        var copied: ArrayList<OnDataChangeListener>? = null
        synchronized(LOCK) {
            copied = ArrayList(listenerList)
        }
        ThreadUtils.launchOnMain {
            for (listener in copied!!) {
                listener.onDataChange()
            }
        }
    }

    open fun getSelectedItem(): List<ITEM> {
        return emptyList()
    }

    fun getItemCount(): Int {
        return 0
    }

    interface OnDataChangeListener {
        fun onDataChange()
    }
}