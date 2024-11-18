package com.example.baseblackboard.blackboard

import com.example.baseblackboard.blackboard.action.ActionPub

fun interface SubscriberListener<T : ActionPub> {
    fun onNotify(data: T?)
}

fun interface SubscriberListen<T> {
    fun onNotify(data: T?)
}