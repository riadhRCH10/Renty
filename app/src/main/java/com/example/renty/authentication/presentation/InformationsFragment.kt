package com.example.renty.authentication.presentation

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.renty.R
import com.example.renty.core.utils.AuthenticationEvent
import com.example.renty.databinding.CompleteInformationsFragmentBinding
import com.example.renty.databinding.SignUpFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class InformationsFragment : Fragment(R.layout.sign_in_fragment) {

    private lateinit var binding : CompleteInformationsFragmentBinding
    private val authenticationViewModel : authenticationViewModel by activityViewModels()
    val safeArgs : InformationsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CompleteInformationsFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            fragmentInformations = this@InformationsFragment
        }

        lifecycleScope.launchWhenCreated {
            authenticationViewModel.authEvents.collect{ event ->
                when(event) {
                    is AuthenticationEvent.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.etEmailLayout.error = null
                        binding.etPasswordLayout.error = null
                        binding.etConfirmPasswordLayout.error = null
                    }
                    is AuthenticationEvent.Failure -> {
                        Toast.makeText(this@InformationsFragment.context, event.errorText, Toast.LENGTH_LONG).show()
                        binding.progressBar.isVisible = false
                        binding.etEmailLayout.error = null
                        binding.etPasswordLayout.error = null
                        binding.etConfirmPasswordLayout.error = null
                    }
                    is AuthenticationEvent.Success -> {
                        Toast.makeText(this@InformationsFragment.context, event.resultText, Toast.LENGTH_LONG).show()
                        navigateToLoginFragment()
                        binding.progressBar.isVisible = false
                        binding.etEmailLayout.error = null
                        binding.etPasswordLayout.error = null
                        binding.etConfirmPasswordLayout.error = null
                    }
                    is AuthenticationEvent.Empty -> {
                        Toast.makeText(this@InformationsFragment.context, "empty state", Toast.LENGTH_LONG).show()
                        binding.progressBar.isVisible = false
                        binding.etEmailLayout.error = null
                        binding.etPasswordLayout.error = null
                        binding.etConfirmPasswordLayout.error = null
                    }
                }
            }
        }

    }

    fun register() {
        if (binding.etEmailText.text.isNullOrEmpty()) {
            binding.etEmailLayout.error = "Email is required"
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmailText.text.toString()).matches()) {
            binding.etEmailLayout.error = "Email is not valid"
            return
        }
        if (binding.etPasswordText.text.isNullOrEmpty()) {
            binding.etEmailLayout.error = null
            binding.etPasswordLayout.error = "Password is required"
            return
        }
        if (binding.etConfirmPasswordText.text.isNullOrEmpty()) {
            binding.etEmailLayout.error = null
            binding.etPasswordLayout.error = null
            binding.etConfirmPasswordLayout.error = "Tap your password again"
        }
        if (binding.etConfirmPasswordText.text.toString() != binding.etPasswordText.text.toString()) {
            binding.etEmailLayout.error = null
            binding.etPasswordLayout.error = null
            binding.etConfirmPasswordLayout.error = "passwords are not correct"
        }

        authenticationViewModel.register(
            Name = binding.etNameText.text.toString(),
            LastName = binding.etLastNameText.text.toString(),
            Email = binding.etEmailText.text.toString(),
            Password = binding.etPasswordText.text.toString(),
            Phone = safeArgs.phone
        )
    }

    //helpers functions

    override fun onPause() {
        super.onPause()
        lifecycleScope.cancel()
    }

    //navigation

    fun navigateBackToSignUpFragment() {
        authenticationViewModel.restartEvents()
        findNavController().navigate(R.id.action_informationsFragment_to_signUpFragment)
    }

    fun navigateToLoginFragment() {
        authenticationViewModel.restartEvents()
        findNavController().navigate(R.id.action_informationsFragment_to_logInFragment)
    }

}