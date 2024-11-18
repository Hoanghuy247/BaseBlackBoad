package com.example.baseblackboard.ui.abstraction

import android.content.Context
import androidx.annotation.CallSuper
import com.example.baseblackboard.activity.abstraction.BaseActivity
import com.example.baseblackboard.blackboard.ISubscriber
import com.example.baseblackboard.blackboard.action.ActionSub
import com.example.baseblackboard.extension.finishFragment
import com.example.baseblackboard.utils.lgi

open class BaseDelegate<F: BaseFragment<*>, VM: BaseViewModel>(m: BaseDelegateManager<F, VM>) : ISubscriber {

    private var mManager: BaseDelegateManager<F, VM>? = m

    protected val mFragment: F? get() = mManager?.fragment
    protected val mViewModel: VM? get() = mManager?.viewModel

    protected val mActivity: BaseActivity<*>? get() = mFragment?.activity()
    protected val mContext: Context? get() = mFragment?.requireContext()

    private var isDestroy = false

    init {
        lgi("onCreateDelegate")
    }

    @CallSuper
    open fun onCreate() {
        isDestroy = false
    }

    open fun onCreateView() {}

    open fun onViewCreated() {}

    open fun onResume() {}

    open fun onPause() {}

    open fun onViewObserver() {}

    open fun onDestroyView() {}

    @CallSuper
    open fun onDestroy() {
        isDestroy = true
        mManager = null
    }

    open fun createSubscriberList(): List<ActionSub<*>> {
        return emptyList()
    }

    override fun registerAction(subscriberInfo: ActionSub<*>) {
        mManager?.registerAction(subscriberInfo)
    }

    override fun registerActionUI(subscriberInfo: ActionSub<*>) {
        mManager?.registerActionUI(subscriberInfo)
    }

    override fun registerActionCurrent(subscriberInfo: ActionSub<*>) {
        mManager?.registerActionCurrent(subscriberInfo)
    }

    override fun registerActionBackGround(subscriberInfo: ActionSub<*>) {
        mManager?.registerActionBackGround(subscriberInfo)
    }

    override fun unSubscriber(key: String) {
        mManager?.unSubscriber(key)
    }

    fun finishFragment() {
        mFragment?.finishFragment()
    }
}