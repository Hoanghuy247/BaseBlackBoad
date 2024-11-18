package com.example.baseblackboard.blackboard.action

import com.example.baseblackboard.blackboard.SubscriberListener

sealed class ActionSub<T : ActionPub>(key: String, val listener: SubscriberListener<T>? = null): Action(key) {

    class DEMO(listener: SubscriberListener<ActionPub.DEMO>? = null): ActionSub<ActionPub.DEMO>(ActionKey.KEY_DEMO, listener)

    var isWorkingOnUI = false
        private set
    var isWorkingOnCurrent = false
        private set
    var isOnce = false
        private set

    fun setWorkingOnUI(): ActionSub<T> {
        isWorkingOnUI = true
        isWorkingOnCurrent = false
        return this
    }

    fun setWorkingOnCurrent(): ActionSub<T> {
        isWorkingOnUI = false
        isWorkingOnCurrent = true
        return this
    }

    fun setWorkingOnBackGround(): ActionSub<T> {
        isWorkingOnUI = false
        isWorkingOnCurrent = false
        return this
    }

    fun setOnce(): ActionSub<T> {
        isOnce = true
        return this
    }
}