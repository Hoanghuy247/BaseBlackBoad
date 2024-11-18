package com.example.baseblackboard.blackboard

import com.example.baseblackboard.blackboard.action.ActionPub
import com.example.baseblackboard.blackboard.action.ActionSub
import com.example.baseblackboard.utils.ThreadUtils
import com.example.baseblackboard.utils.lgi
import java.util.concurrent.ConcurrentHashMap

object BlackBoard {

    private const val TAG = "LocalBroadCast"
    private val LOCK_SUBCRIBER = Any()

    private const val THREAD_UI = 0
    private const val THREAD_BG = 1
    private const val THREAD_ON_CURRENT = 2

    private val mMap: HashMap<String, ArrayList<ActionSub<*>>> = HashMap()
    private val mData = ConcurrentHashMap<String, ActionPub?>()
    private val mThreadTypeMap = HashMap<ActionSub<*>, Int>()

    fun create() {
        mMap.clear()
        mData.clear()
        mThreadTypeMap.clear()
    }

    fun registerAction(action: ActionSub<*>) {
        val threadId = when {
            action.isWorkingOnUI -> THREAD_UI
            action.isWorkingOnCurrent -> THREAD_ON_CURRENT
            else -> THREAD_BG
        }
        registerAction(action, threadId)
    }

    private fun registerAction(action: ActionSub<*>, threadId: Int) {
        synchronized(LOCK_SUBCRIBER) {
            var subscriberList = mMap[action.actionKey]
            if (subscriberList == null) {
                subscriberList = ArrayList()
            }
            if (!subscriberList.contains(action)) {
                subscriberList.add(action)
            }
            mMap[action.actionKey] = subscriberList
            mThreadTypeMap[action] = threadId
        }
    }

    fun unregisterAction(action: ActionSub<*>) {
        synchronized(LOCK_SUBCRIBER) {
            val subscriberList = mMap[action.actionKey]
            if (subscriberList != null) {
                subscriberList.remove(action)
                mMap[action.actionKey] = subscriberList
            }
            mThreadTypeMap[action] = -1
            mThreadTypeMap.remove(action)
        }
    }

    fun sendBroadCast(actionPub: ActionPub) {
        lgi("sendBroadCast: ${actionPub.actionKey}")
        sendBoardCast(actionPub, true)
    }

    fun sendEvent(actionPub: ActionPub) {
        lgi("sendBroadCast: ${actionPub.actionKey}")
        sendBoardCast(actionPub, false)
    }

    private fun sendBoardCast(actionPub: ActionPub, save: Boolean) {
        if (save) {
            mData[actionPub.actionKey] = actionPub
        }
        var copiedList: ArrayList<ActionSub<*>>? = null
        synchronized(LOCK_SUBCRIBER) {
            val actionList = mMap[actionPub.actionKey]
            if (actionList != null) {
                copiedList = actionList
            }
        }

        if (copiedList != null) {
            for (action in copiedList!!) {
                notify(action, actionPub)
            }
        }
    }

    private fun notify(action: ActionSub<*>, data: ActionPub?) {
        when (mThreadTypeMap[action]) {
            THREAD_UI -> ThreadUtils.launchOnMain {
                (action as ActionSub<ActionPub>).listener?.onNotify(data)
            }
            THREAD_BG -> ThreadUtils.launchOnIO {
                (action as ActionSub<ActionPub>).listener?.onNotify(data)
            }
            THREAD_ON_CURRENT -> {
                (action as ActionSub<ActionPub>).listener?.onNotify(data)
            }
        }
        if (action.isOnce) {
            unregisterAction(action)
        }
    }

    fun <T: ActionPub> read(sub: ActionSub<T>): T? {
        return mData[sub.actionKey] as? T?
    }
}