package com.example.renty.authentication.presentation

import android.content.Intent
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
import com.example.renty.host.presentation.HostActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LogInFragment : Fragment(R.layout.sign_in_fragment) {

    private lateinit var binding : SignInFragmentBinding
    private val authenticationViewModel : authenticationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SignInFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            fragmentLogIn = this@LogInFragment
        }

        lifecycleScope.launchWhenCreated {
            authenticationViewModel.authEvents.collect{ event ->
                when(event) {
                    is AuthenticationEvent.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.etPhoneLayout.error = null
                        binding.etPasswordLayout.error = null
                    }
                    is AuthenticationEvent.Failure -> {
                        Toast.makeText(this@LogInFragment.context, event.errorText, Toast.LENGTH_LONG).show()
                        binding.progressBar.isVisible = false
                        when(event.errorCode) {
                            400 -> {
                                binding.etPhoneLayout.error = "user not registered"
                            }
                            401 -> {
                                binding.etPasswordLayout.error = "wrong password"
                            }
                        }
                    }
                    is AuthenticationEvent.Success -> {
                        Toast.makeText(this@LogInFragment.context, event.resultText, Toast.LENGTH_LONG).show()
                        binding.progressBar.isVisible = false
                        binding.etPhoneLayout.error = null
                        binding.etPasswordLayout.error = null

                        when(event.data?.user_type) {
                            "1" -> {
                                navigateToSindicComponent()
                            }
                            "2" -> {

                            }
                        }
                    }
                    is AuthenticationEvent.Empty -> {
                        Toast.makeText(this@LogInFragment.context, "empty state", Toast.LENGTH_LONG).show()
                        binding.progressBar.isVisible = false
                        binding.etPhoneLayout.error = null
                        binding.etPasswordLayout.error = null
                    }
                }
            }
        }

    }

    override fun onPause() {
        super.onPause()
        lifecycleScope.cancel()
    }

    fun login() {
        if (binding.etPhoneText.text.isNullOrEmpty()) {
            binding.etPhoneLayout.error = "Phone is required"
            return
        }
        if (binding.etPasswordText.text.isNullOrEmpty()) {
            binding.etPhoneLayout.error = null
            binding.etPasswordLayout.error = "Password is required"
            return
        }
        authenticationViewModel.login(
            Phone = binding.etPhoneText.text.toString(),
            Password = binding.etPasswordText.text.toString()
        )
    }

    //navigation
    fun navigateToSignUp() {
        authenticationViewModel.restartEvents()
        findNavController().navigate(R.id.action_logInFragment_to_signUpFragment)
    }

    fun navigateToSindicComponent() {
        authenticationViewModel.restartEvents()
        findNavController().navigate(R.id.action_logInFragment_to_syndicFragment)
    }

}