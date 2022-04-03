package com.example.renty.host.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.renty.R
import com.example.renty.databinding.FragmentHomeBinding
import com.example.renty.host.presentation.home.syndic.SyndicFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)
    private var _bindingHome: FragmentHomeBinding? = null
    private lateinit var userId: String

    private val role = 2


    private val bindingHome get() = _bindingHome!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _bindingHome = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = bindingHome.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getRole()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingHome = null
    }

    private fun getRole(){
        val navController = findNavController()
        when(role) {

            1-> {

                navController.navigate(R.id.hilt_ResidentFragment)
                navController.popBackStack(R.id.hilt_SyndicFragment,true)
            }
            2-> {
                navController.navigate(R.id.hilt_SyndicFragment)
                navController.popBackStack(R.id.hilt_ResidentFragment,true)


            }
        }

    }


}