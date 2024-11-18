package com.example.baseblackboard.blackboard

import androidx.annotation.CallSuper
import com.example.baseblackboard.blackboard.action.ActionSub

interface ISubscriberImpl : ISubscriber {

    val mSubscriberList: ArrayList<ActionSub<*>>

    override fun registerAction(subscriberInfo: ActionSub<*>) {
        replaceSubscriber(subscriberInfo.actionKey, subscriberInfo)
    }

    override fun registerActionUI(subscriberInfo: ActionSub<*>) {
        subscriberInfo.setWorkingOnUI()
        replaceSubscriber(subscriberInfo.actionKey, subscriberInfo)
    }

    override fun registerActionCurrent(subscriberInfo: ActionSub<*>) {
        subscriberInfo.setWorkingOnCurrent()
        replaceSubscriber(subscriberInfo.actionKey, subscriberInfo)
    }

    override fun registerActionBackGround(subscriberInfo: ActionSub<*>) {
        subscriberInfo.setWorkingOnBackGround()
        replaceSubscriber(subscriberInfo.actionKey, subscriberInfo)
    }

    override fun unSubscriber(key: String) {
        val keyInfo = findSubscriber(getSubscriberList(), key)
        if (keyInfo != null) {
            mSubscriberList.remove(keyInfo)
            BlackBoard.unregisterAction(keyInfo)
        }
    }

    fun replaceSubscriber(key: String, subscriberInfo: ActionSub<*>) {
        unSubscriber(key)
        mSubscriberList.add(subscriberInfo)
        subscribe(subscriberInfo)
    }

    @CallSuper
    fun onCreateSubscriber() {
        subscribe()
    }

    private fun subscribe() {
        val subscriberList = getSubscriberList()
        for (action in subscriberList) {
            subscribe(action)
        }
    }

    private fun subscribe(actionSub: ActionSub<*>) {
        BlackBoard.registerAction(actionSub)
    }

    fun createSubscriberList(list: ArrayList<ActionSub<*>>)

    private fun getSubscriberList(): ArrayList<ActionSub<*>> {
        if (mSubscriberList.size == 0) {
            createSubscriberList(mSubscriberList)
        }
        return mSubscriberList
    }

    private fun findSubscriber(list: ArrayList<ActionSub<*>>?, key: String) : ActionSub<*>? {
        if (list != null) {
            for (action in list) {
                if (action.actionKey == key) {
                    return action
                }
            }
        }
        return null
    }

    @CallSuper
    fun onDestroySubscriber() {
        unSubscriber()
    }

    private fun unSubscriber() {
        val subscriberList = mSubscriberList
        for (info in subscriberList) {
            BlackBoard.unregisterAction(info)
        }
        mSubscriberList.clear()
    }

    open fun isDestroy(): Boolean {
        return false
    }
}