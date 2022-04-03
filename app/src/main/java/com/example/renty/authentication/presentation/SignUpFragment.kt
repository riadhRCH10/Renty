package com.example.renty.authentication.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.renty.R
import com.example.renty.core.utils.AuthenticationEvent
import com.example.renty.databinding.SignInFragmentBinding
import com.example.renty.databinding.SignUpFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.sign_in_fragment) {

    private lateinit var binding : SignUpFragmentBinding
    private val authenticationViewModel : authenticationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SignUpFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            fragmentSignUp = this@SignUpFragment
        }

        lifecycleScope.launchWhenCreated {
            authenticationViewModel.authEvents.collect{ event ->
                when(event) {
                    is AuthenticationEvent.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.etPhoneLayout.error = null
                    }
                    is AuthenticationEvent.Failure -> {
                        Toast.makeText(this@SignUpFragment.context, event.errorText, Toast.LENGTH_LONG).show()
                        binding.progressBar.isVisible = false
                        binding.etPhoneLayout.error = "Phone not saved"
                    }
                    is AuthenticationEvent.Success -> {
                        navigateToInformationsFragment()
                        /*
                        Toast.makeText(this@SignUpFragment.context, event.resultText, Toast.LENGTH_LONG).show()
                        binding.progressBar.isVisible = false
                        binding.etPhoneLayout.error = null
                         */
                    }
                    is AuthenticationEvent.Empty -> {
                        Toast.makeText(this@SignUpFragment.context, "empty state", Toast.LENGTH_LONG).show()
                        binding.progressBar.isVisible = false
                        binding.etPhoneLayout.error = null
                    }
                }
            }
        }

    }

    override fun onPause() {
        super.onPause()
        lifecycleScope.cancel()
    }

    fun verify() {
        if (binding.etPhoneText.text.isNullOrEmpty()) {
            binding.etPhoneLayout.error = "Phone is required"
            return
        }
        authenticationViewModel.phoneVerification(
            binding.etPhoneText.text.toString()
        )
    }

    //navigation

    fun navigateBackToLogIn() {
        authenticationViewModel.restartEvents()
        findNavController().navigate(R.id.action_signUpFragment_to_logInFragment)
    }

    fun navigateToInformationsFragment() {
        authenticationViewModel.restartEvents()
        val action = SignUpFragmentDirections.actionSignUpFragmentToInformationsFragment(binding.etPhoneText.text.toString())
        findNavController().navigate(action)
    }
}