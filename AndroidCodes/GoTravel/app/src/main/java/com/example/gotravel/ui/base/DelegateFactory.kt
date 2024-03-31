package com.example.gotravel.ui.base

import java.util.EnumMap

open class DelegateFactory<F : BaseFragment<*>, VM : BaseViewModel>(
    protected val fragment : F,
    protected val viewModel : VM
) {
    private val mapDelegate: EnumMap<KeyDelegate, BaseDelegate<F, VM>> = EnumMap(KeyDelegate::class.java)

    fun init(){
        createDelegateList().forEach {
            mapDelegate[it.first] = it.second as BaseDelegate<F, VM>
        }
    }

    fun onViewCreated() {
        getDelegate().forEach {
            it.onViewCreated()
        }
    }

    open fun createDelegateList(): List<Pair<KeyDelegate, BaseDelegate<F,VM>>> {
        return emptyList()
    }

    private fun getDelegate() : List<BaseDelegate<F, VM>> {
        return mapDelegate.values.toMutableList()
    }
}