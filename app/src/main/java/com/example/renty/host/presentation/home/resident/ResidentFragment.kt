package com.example.renty.host.presentation.home.resident

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.renty.R
import com.example.renty.databinding.FragmentHomeBinding
import com.example.renty.databinding.HomeResidentBinding
import com.example.renty.host.presentation.home.resident.ResidentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResidentFragment : Fragment() {

    private val homeViewModel: ResidentViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)
    private var _bindingResidentHome: HomeResidentBinding? = null


    private val bindingHome get() = _bindingResidentHome!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _bindingResidentHome = HomeResidentBinding.inflate(inflater, container, false)
        val root: View = bindingHome.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingResidentHome = null
    }

    fun setupUi() {


    }
}