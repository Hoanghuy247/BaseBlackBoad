package com.example.baseblackboard.blackboard

class SubscriberManager(private val mSubscriberList: MutableList<Subscriber>){

    fun onCreate() {
        for (subscriber in mSubscriberList) {
            subscriber.onCreateSubscriber()
        }
    }

    fun add(subscriber: Subscriber) {
        mSubscriberList.add(subscriber)
    }

    fun onDestroy() {
        for (subscriber in mSubscriberList) {
            subscriber.onDestroySubscriber()
        }
    }
}