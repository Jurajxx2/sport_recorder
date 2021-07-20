package com.example.sportrecorder.screens.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.example.sportrecorder.R
import com.example.sportrecorder.base.BaseActivity
import com.example.sportrecorder.databinding.ActivitySplashBinding
import com.example.sportrecorder.screens.login.LoginActivity
import com.example.sportrecorder.screens.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity: BaseActivity<SplashViewModel, ActivitySplashBinding>() {

    override val layout = R.layout.activity_splash
    override val viewModel: SplashViewModel by viewModel()

    override fun setupActivity() {
        binding.vm = viewModel

        viewModel.user.observe(this, {
            if (it == null) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }
}