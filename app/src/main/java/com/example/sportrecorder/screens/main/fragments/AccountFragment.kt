package com.example.sportrecorder.screens.main.fragments

import android.content.Intent
import com.example.sportrecorder.R
import com.example.sportrecorder.base.BaseActivity
import com.example.sportrecorder.base.BaseFragment
import com.example.sportrecorder.databinding.ActivityLoginBinding
import com.example.sportrecorder.databinding.FragmentAccountBinding
import com.example.sportrecorder.screens.login.LoginViewModel
import com.example.sportrecorder.screens.main.MainViewModel
import com.example.sportrecorder.screens.splash.SplashActivity
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment: BaseFragment<MainViewModel, FragmentAccountBinding>() {

    override val layout = R.layout.fragment_account
    override val viewModel: MainViewModel by sharedViewModel()

    override fun setup() {
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.user.observe(this, {
            if (it == null) {
                requireActivity().startActivity(Intent(requireContext(), SplashActivity::class.java))
                requireActivity().finish()
            }
        })
    }
}