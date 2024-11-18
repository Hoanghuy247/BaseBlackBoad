package com.example.baseblackboard.ui.abstraction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.baseblackboard.activity.abstraction.BaseActivity
import com.example.baseblackboard.blackboard.ISubscriberImpl
import com.example.baseblackboard.blackboard.action.ActionSub
import com.example.baseblackboard.utils.lgi

typealias InflateFragment<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragmentNoViewModel<B: ViewDataBinding> (private val inflate: InflateFragment<B>)
    : Fragment(), ISubscriberImpl {

    private var _binding: B? = null
    val mBinding get() = _binding

    override val mSubscriberList: ArrayList<ActionSub<*>> = ArrayList()

    override fun createSubscriberList(list: ArrayList<ActionSub<*>>) {
    }

    private var isNewBinding = true
    protected var mViewReady: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lgi("onCreate#")
        onCreateSubscriber()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (_binding == null) {
            lgi("onCreateView#")
            _binding = inflate.invoke(inflater, container, false)
            isNewBinding = true
            mViewReady = true
        } else {
            isNewBinding = false
            mBinding?.lifecycleOwner = viewLifecycleOwner
            onBeforeCreateViewDone(savedInstanceState)
        }

        return mBinding!!.root
    }

    open fun onBeforeCreateViewDone(savedInstanceState: Bundle?) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lgi("onViewCreated#")
        if (!mViewReady) {
            mViewReady = true
            onBeforeViewCreated(savedInstanceState)
            onBindView()
        }
        onViewObserver()
    }

    open fun onBeforeViewCreated(savedInstanceState: Bundle?) {}

    override fun onDestroyView() {
        super.onDestroyView()
        lgi("onDestroyView")
    }

    override fun onDestroy() {
        lgi("onDestroy")
        _binding = null
        mViewReady = false
        super.onDestroy()
    }

    override fun isDestroy(): Boolean {
        return _binding == null
    }

    open fun onBindView() {}

    open fun onViewObserver() {}

    fun activity(): BaseActivity<*> {
        return requireActivity() as BaseActivity<*>
    }
}