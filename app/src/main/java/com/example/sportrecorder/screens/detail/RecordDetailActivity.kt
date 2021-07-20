package com.example.sportrecorder.screens.detail

import android.content.Intent
import com.example.sportrecorder.R
import com.example.sportrecorder.base.BaseActivity
import com.example.sportrecorder.databinding.ActivityLoginBinding
import com.example.sportrecorder.databinding.ActivityRecordDetailBinding
import com.example.sportrecorder.screens.login.LoginViewModel
import com.example.sportrecorder.screens.main.MainActivity
import com.example.sportrecorder.screens.register.RegisterActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecordDetailActivity: BaseActivity<RecordDetailViewModel, ActivityRecordDetailBinding>() {

    override val layout = R.layout.activity_record_detail
    override val viewModel: RecordDetailViewModel by viewModel()

    companion object{
        const val RECORD_ITEM_KEY = "record_item_key"
    }

    override fun setupActivity() {
        binding.vm = viewModel
    }
}