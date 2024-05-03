package com.example.renty.homeResident.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.renty.R
import com.example.renty.databinding.FragmentHomeBinding

class HomeResidentFragment : Fragment() {

    private var _bindingHome: FragmentHomeBinding? = null
    private val bindingHome get() = _bindingHome!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingHome = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = bindingHome.root

        bindingHome.textView18.setOnClickListener {
            navigateToHomeSyndic()
        }

        bindingHome.btnRent.setOnClickListener {
            navigateToHomeSyndic()
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //getRole()


    }

    fun navigateToHomeSyndic() {
        findNavController().navigate(R.id.action_homeResidentFragment_to_syndicFragment)
    }


}