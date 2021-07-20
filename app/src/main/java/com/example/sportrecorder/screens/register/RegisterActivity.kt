package com.example.sportrecorder.screens.register

import android.content.Intent
import com.example.sportrecorder.R
import com.example.sportrecorder.base.BaseActivity
import com.example.sportrecorder.databinding.ActivityLoginBinding
import com.example.sportrecorder.databinding.ActivityRegisterBinding
import com.example.sportrecorder.screens.login.LoginActivity
import com.example.sportrecorder.screens.login.LoginViewModel
import com.example.sportrecorder.screens.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity: BaseActivity<RegisterViewModel, ActivityRegisterBinding>() {

    override val layout = R.layout.activity_register
    override val viewModel: RegisterViewModel by viewModel()

    override fun setupActivity() {
        binding.vm = viewModel
        binding.lifecycleOwner = this

        viewModel.user.observe(this, {
            if (it != null) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })

        binding.buttonSignIn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}