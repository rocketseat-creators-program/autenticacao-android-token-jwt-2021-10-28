package com.expertsclub.expertsauthentication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navArgument
import com.expertsclub.expertsauthentication.R
import com.expertsclub.expertsauthentication.databinding.HomeFragmentBinding
import com.expertsclub.expertsauthentication.ui.MainActivity
import com.expertsclub.expertsauthentication.ui.UserViewModel
import com.expertsclub.expertsauthentication.ui.login.LoginFragment

class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding: HomeFragmentBinding get() = _binding!!

    private val userViewModel: UserViewModel by viewModels {
        UserViewModel.UserViewModelFactory(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = HomeFragmentBinding.inflate(inflater).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        setListeners()
        userViewModel.getUser()
    }

    private fun observeData() {
        userViewModel.authenticationState.observe(viewLifecycleOwner) { authenticationState ->
            when (authenticationState) {
                is UserViewModel.AuthenticationState.Authenticated -> {
                    binding.run {
                        textUserName.text = getString(
                            R.string.welcome,
                            authenticationState.user.displayName
                        )
                        flipperHome.displayedChild = FLIPPER_SHOW_CONTENT
                    }
                }
                UserViewModel.AuthenticationState.Unauthenticated ->
                    findNavController().navigate(R.id.loginFragment)
                else -> binding.flipperHome.displayedChild = FLIPPER_SHOW_LOADING
            }
        }
    }

    private fun setListeners() {
        binding.buttonLogOut.setOnClickListener {
            userViewModel.logout()
        }
    }

    companion object {
        private const val FLIPPER_SHOW_LOADING = 0
        private const val FLIPPER_SHOW_CONTENT = 1
    }
}