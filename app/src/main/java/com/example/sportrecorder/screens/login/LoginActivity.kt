package com.example.sportrecorder.screens.login

import android.content.Intent
import android.view.MenuItem
import com.example.sportrecorder.R
import com.example.sportrecorder.base.BaseActivity
import com.example.sportrecorder.databinding.ActivityLoginBinding
import com.example.sportrecorder.screens.main.MainActivity
import com.example.sportrecorder.screens.register.RegisterActivity
import com.google.android.material.navigation.NavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity: BaseActivity<LoginViewModel, ActivityLoginBinding>() {

    override val layout = R.layout.activity_login
    override val viewModel: LoginViewModel by viewModel()

    override fun setupActivity() {
        binding.vm = viewModel
        binding.lifecycleOwner = this

        viewModel.user.observe(this, {
            if (it != null) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })

        binding.buttonSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }
}