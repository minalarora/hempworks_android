package com.hemp.works.dashboard.support.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.hemp.works.R
import com.hemp.works.databinding.FragmentAllSupportBinding
import java.net.URLEncoder


class AllSupportFragment : Fragment() {

    private lateinit var binding: FragmentAllSupportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white);
        binding = DataBindingUtil.inflate<FragmentAllSupportBinding>(
            inflater, R.layout.fragment_all_support, container, false)

        binding.bottomNavigation.setOnNavigationItemSelectedListener{
            selectBottomItem(it)
            true
        }

        binding.chat.setOnClickListener {

        }

        binding.email.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:info@hempworks.in")
            }
            startActivity(Intent.createChooser(emailIntent, ""))
        }

        binding.whatsapp.setOnClickListener {

            try {
                val i = Intent(Intent.ACTION_VIEW)
                val url =
                    "https://api.whatsapp.com/send?phone=" + "+918871748278"
                i.setPackage("com.whatsapp")
                i.data = Uri.parse(url)
                startActivity(i)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNavigation.selectedItemId = R.id.support
    }


    private fun selectBottomItem(menuItem: MenuItem) {

        when(menuItem.itemId) {
            R.id.home -> {
                AllSupportFragmentDirections.actionAllSupportFragmentToHomeFragment().let {
                    binding.root.findNavController().navigate(it)
                }
            }
            R.id.products -> {
                AllSupportFragmentDirections.actionAllSupportFragmentToAllProductListFragment().let {
                    binding.root.findNavController().navigate(it)
                }
            }
            R.id.support -> {

            }
            R.id.account -> {
                AllSupportFragmentDirections.actionAllSupportFragmentToAccountFragment().let {
                    binding.root.findNavController().navigate(it)
                }
            }


            //TODO: NAVIGATE TO DIFF FRAGMENTS
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            AllSupportFragment()
    }
}