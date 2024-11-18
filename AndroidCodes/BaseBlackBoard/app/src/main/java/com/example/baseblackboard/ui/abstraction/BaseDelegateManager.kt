package com.example.baseblackboard.ui.abstraction

import com.example.baseblackboard.blackboard.ISubscriber
import com.example.baseblackboard.blackboard.action.ActionSub
import java.lang.ref.WeakReference
import java.util.EnumMap

open class BaseDelegateManager<F: BaseFragment<*>, VM: BaseViewModel>(f: F, vm: VM): ISubscriber {

    protected val f: WeakReference<F> = WeakReference(f)
    private val vm: WeakReference<VM> = WeakReference(vm)

    val fragment: F? get() = f.get()
    val viewModel: VM? get() = vm.get()

    private var isDestroy = false
    private var mapDelegate: EnumMap<KeyDelegate, BaseDelegate<F, VM>> = EnumMap(KeyDelegate::class.java)

    fun init() {
        isDestroy = false
        createDelegateList().forEach {
            mapDelegate[it.first] = it.second
        }
    }

    fun onCreate() {
        getDelegates().forEach {
            it.onCreate()
        }
    }

    fun onCreateView() {
        getDelegates().forEach {
            it.onCreateView()
        }
    }

    fun onViewCreated() {
        getDelegates().forEach {
            it.onViewCreated()
        }
    }

    fun onViewObserver() {
        getDelegates().forEach {
            it.onViewObserver()
        }
    }

    fun onResume() {
        getDelegates().forEach {
            it.onResume()
        }
    }

    fun onPause() {
        getDelegates().forEach {
            it.onPause()
        }
    }

    fun onDestroyView() {
        getDelegates().forEach {
            it.onDestroyView()
        }
    }

    fun onDestroy() {
        getDelegates().forEach {
            it.onDestroy()
        }
        mapDelegate.clear()
        isDestroy = true
    }

    fun createSubscriberList() {
        getDelegates().forEach {
            it.createSubscriberList().forEach {
                actionSub -> registerAction(actionSub)
            }
        }
    }

    open fun createDelegateList(): List<Pair<KeyDelegate, BaseDelegate<F, VM>>> {
        return emptyList()
    }

    private fun getDelegates(): List<BaseDelegate<F,VM>> {
        return mapDelegate.values.toMutableList()
    }

    override fun registerAction(subscriberInfo: ActionSub<*>) {
        fragment?.registerAction(subscriberInfo)
    }

    override fun registerActionUI(subscriberInfo: ActionSub<*>) {
        fragment?.registerActionUI(subscriberInfo)
    }

    override fun registerActionCurrent(subscriberInfo: ActionSub<*>) {
        fragment?.registerActionCurrent(subscriberInfo)
    }

    override fun registerActionBackGround(subscriberInfo: ActionSub<*>) {
        fragment?.registerActionBackGround(subscriberInfo)
    }

    override fun unSubscriber(key: String) {
        fragment?.unSubscriber(key)
    }
}