package com.example.baseblackboard.activity.abstraction

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.example.baseblackboard.blackboard.ISubscriberImpl
import com.example.baseblackboard.blackboard.action.ActionSub
import com.example.baseblackboard.utils.lgi

typealias InflateActivity<T> = (LayoutInflater) -> T

abstract class BaseActivity<B: ViewDataBinding> (private val inflater: InflateActivity<B>):
    AppCompatActivity(),  ISubscriberImpl {

    lateinit var mBinding: B
        private set

    override val mSubscriberList: ArrayList<ActionSub<*>> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lgi("onCreateActivity#")
        mBinding = inflater.invoke(layoutInflater)
        onCreateSubscriber()
    }

    override fun onResume() {
        super.onResume()
        lgi("onResume")
    }

    override fun onPause() {
        super.onPause()
        lgi("onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        lgi("onDestroy")
    }

    override fun createSubscriberList(list: ArrayList<ActionSub<*>>) {

    }
}