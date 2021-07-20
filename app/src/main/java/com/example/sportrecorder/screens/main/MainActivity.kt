package com.example.sportrecorder.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.FragmentTransaction
import com.example.sportrecorder.R
import com.example.sportrecorder.base.BaseActivity
import com.example.sportrecorder.databinding.ActivityLoginBinding
import com.example.sportrecorder.databinding.ActivityMainBinding
import com.example.sportrecorder.screens.login.LoginViewModel
import com.example.sportrecorder.screens.main.adapters.ScreenSlidePagerAdapter
import com.example.sportrecorder.screens.main.fragments.AccountFragment
import com.example.sportrecorder.screens.main.fragments.RecordListFragment
import com.example.sportrecorder.screens.main.fragments.RecordNewFragment
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity: BaseActivity<MainViewModel, ActivityMainBinding>(), NavigationBarView.OnItemSelectedListener {

    override val layout = R.layout.activity_main
    override val viewModel: MainViewModel by viewModel()

    override fun setupActivity() {
        binding.vm = viewModel

        binding.navigationView.setOnItemSelectedListener(this)

        val pagerAdapter = ScreenSlidePagerAdapter(this)
        binding.viewPager.apply {
            adapter = pagerAdapter
            isUserInputEnabled = false
            offscreenPageLimit = 2
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val position = when(item.itemId) {
            R.id.recordsList -> 0
            R.id.recordsNew -> 1
            R.id.account -> 2
            else -> 0
        }

        binding.viewPager.setCurrentItem(position, true)
        return true
    }
}