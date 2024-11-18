package com.example.baseblackboard.ui.abstraction

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import com.example.baseblackboard.blackboard.action.ActionSub


abstract class BaseFragment<B: ViewDataBinding>(inflater: InflateFragment<B>) :
    BaseFragmentNoViewModel<B> (inflater) {

    abstract val mViewModel: BaseViewModel
    protected lateinit var mDelegateManager: BaseDelegateManager<*,*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @CallSuper
    override fun onBeforeCreateViewDone(savedInstanceState: Bundle?) {
        mDelegateManager = createDelegateManager()
        mDelegateManager.init()
        mDelegateManager.onCreate()
        mDelegateManager.onCreateView()
        mDelegateManager.createSubscriberList()
    }

    open fun createDelegateManager(): BaseDelegateManager<*,*> {
        return BaseDelegateManager(this, mViewModel)
    }

    override fun onBeforeViewCreated(savedInstanceState: Bundle?) {
        mDelegateManager.onViewCreated()
    }

    override fun onViewObserver() {
        mDelegateManager.onViewObserver()
    }


    override fun createSubscriberList(list: ArrayList<ActionSub<*>>) {

    }

}