package com.expertsclub.expertsauthentication.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.expertsclub.expertsauthentication.R
import com.expertsclub.expertsauthentication.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {

    private var _binding: LoginFragmentBinding? = null
    private val binding: LoginFragmentBinding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModel.LoginViewModelFactory(requireContext().applicationContext)
    }

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = LoginFragmentBinding.inflate(inflater).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        requireActivity().run {
            onBackPressedDispatcher.addCallback(this) {
                finish()
            }
        }

        viewModel.loginStateData.observe(viewLifecycleOwner) { state ->
            when (state) {
                LoginViewModel.LoginState.ShowLoading -> {
                    binding.run {
                        flipperActionLogin.displayedChild = FLIPPER_POSITION_LOADING
                        textError.isVisible = false
                    }
                }
                LoginViewModel.LoginState.LoginSuccess -> {
                    binding.flipperActionLogin.displayedChild = FLIPPER_POSITION_BUTTON
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(R.id.homeFragment, true)
                        .build()
                    navController.navigate(R.id.homeFragment, null, navOptions)
                }
                LoginViewModel.LoginState.ShowError -> {
                    binding.run {
                        flipperActionLogin.displayedChild = FLIPPER_POSITION_BUTTON
                        textError.isVisible = true
                    }
                }
            }
        }

        binding.buttonLogIn.setOnClickListener {
            val email = binding.inputEmail.text?.toString() ?: ""
            val password = binding.inputPassword.text?.toString() ?: ""

            viewModel.login(email, password)
        }
    }

    companion object {
        private const val FLIPPER_POSITION_BUTTON = 0
        private const val FLIPPER_POSITION_LOADING = 1
    }
}