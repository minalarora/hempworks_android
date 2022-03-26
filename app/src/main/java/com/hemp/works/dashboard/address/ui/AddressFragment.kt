package com.hemp.works.dashboard.address.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hemp.works.R
import com.hemp.works.dashboard.model.CartProduct
import com.hemp.works.di.Injectable
import com.hemp.works.login.data.model.Address


class AddressFragment : Fragment(), Injectable {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_address, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            AddressFragment()
    }
}

interface AddressItemClickListener {

    fun onItemClick(address: Address)

    fun onEditClick(address: Address)

    fun onDeleteClick(address: Address)
}