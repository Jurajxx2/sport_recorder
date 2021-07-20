package com.example.sportrecorder.screens.main.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sportrecorder.screens.main.fragments.AccountFragment
import com.example.sportrecorder.screens.main.fragments.RecordListFragment
import com.example.sportrecorder.screens.main.fragments.RecordNewFragment

class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    val fragments = listOf(RecordListFragment(), RecordNewFragment(), AccountFragment())

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}