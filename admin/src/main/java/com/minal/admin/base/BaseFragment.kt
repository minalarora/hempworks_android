package com.hoobio.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.Koin
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment <VB : ViewBinding> : Fragment(), BaseInterface,KoinComponent,CoroutineScope {


    lateinit var mBinding: VB
    var savedInstanceState : Bundle ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        obViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = getViewBinding(inflater,container,savedInstanceState)
        this.savedInstanceState = savedInstanceState
        onViewInitialized()
        return mBinding.root
    }


    /**
     * It returns [ViewBinding] [VB] which is assigned to [mViewBinding] and used in [onCreateView]
     */
    abstract fun getViewBinding(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): VB

    /**
     *observer viewmodel in @oncreate of fragment
     * this will fresh data of api
     * it will not give last saved value if you want last saved value then you can
     * observe it in onViewCreated or onCreteView*/
    open fun obViewModel(){}

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

}
