package com.example.baseblackboard.blackboard

import com.example.baseblackboard.blackboard.action.ActionSub

interface ISubscriber {

    fun registerAction(subscriberInfo: ActionSub<*>)

    fun registerActionUI(subscriberInfo: ActionSub<*>)

    fun registerActionCurrent(subscriberInfo: ActionSub<*>)

    fun registerActionBackGround(subscriberInfo: ActionSub<*>)

    fun unSubscriber(key: String)
}