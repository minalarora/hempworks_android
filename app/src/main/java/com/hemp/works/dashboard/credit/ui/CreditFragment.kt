package com.hemp.works.dashboard.credit.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hemp.works.R
import com.hemp.works.di.Injectable


class CreditFragment : Fragment(), Injectable {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_credit, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CreditFragment()
    }
}