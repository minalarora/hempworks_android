package com.hemp.works.login.ui
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hemp.works.R
import com.hemp.works.databinding.FragmentCreateBinding
import com.hemp.works.databinding.FragmentSplashBinding
import com.hemp.works.di.Injectable
import com.hemp.works.di.injectViewModel
import com.hemp.works.login.LoginSharedViewModel
import com.hemp.works.login.ui.viewmodel.CreateViewModel
import com.hemp.works.login.ui.viewmodel.SplashViewModel
import javax.inject.Inject


class CreateFragment : Fragment(), Injectable {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sharedViewModel: LoginSharedViewModel
    private lateinit var viewModel: CreateViewModel
    private lateinit var binding: FragmentCreateBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = injectViewModel(viewModelFactory)
        sharedViewModel = requireActivity().injectViewModel(viewModelFactory)

        binding = DataBindingUtil.inflate<FragmentCreateBinding>(
            inflater, R.layout.fragment_create, container, false).apply {
            lifecycleOwner = this@CreateFragment
        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CreateFragment()
    }
}