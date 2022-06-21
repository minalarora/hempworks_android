package com.hoobio.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.KoinComponent

abstract class BaseDialogFragment <VB : ViewBinding> : DialogFragment(), BaseInterface, KoinComponent {

    lateinit var mBinding: VB
    var savedInstanceState : Bundle?= null
//    val mBaseViewModel by sharedViewModel<BaseViewModel>()

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
}