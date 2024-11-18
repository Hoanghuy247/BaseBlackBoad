package com.example.baseblackboard.blackboard.action

sealed class ActionPub(key: String) : Action(key) {
    open class DEMO(val start: Boolean) : ActionPub(ActionKey.KEY_DEMO)
}