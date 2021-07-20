package com.example.sportrecorder.screens.main.fragments

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportrecorder.R
import com.example.sportrecorder.base.BaseFragment
import com.example.sportrecorder.databinding.FragmentRecordListBinding
import com.example.sportrecorder.model.SportRecord
import com.example.sportrecorder.screens.main.MainViewModel
import com.example.sportrecorder.screens.main.adapters.RecordListAdapter
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RecordListFragment: BaseFragment<MainViewModel, FragmentRecordListBinding>() {

    override val layout = R.layout.fragment_record_list
    override val viewModel: MainViewModel by sharedViewModel()

    private val recordListAdapter = RecordListAdapter()

    override fun onResume() {
        super.onResume()
        viewModel.getRecords()
    }

    override fun setup() {
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.recordsList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recordListAdapter
        }

        viewModel.getRecordsError.observe(this, {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })

        viewModel.data.observe(this, {
            it.first?.let { records ->
                val filter = it.second
                val filtered = records.filter { if (filter != null) it.storageType == filter else true }
                recordListAdapter.setItems(filtered)
            }
            when(it.second) {
                null -> binding.filterHolder.getTabAt(0)?.select()
                SportRecord.StorageType.Local -> binding.filterHolder.getTabAt(1)?.select()
                SportRecord.StorageType.Remote -> binding.filterHolder.getTabAt(2)?.select()
            }
        })

        binding.filterHolder.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0 -> { viewModel.filterType.value = null }
                    1 -> { viewModel.filterType.value = SportRecord.StorageType.Local }
                    2 -> { viewModel.filterType.value = SportRecord.StorageType.Remote }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })
    }
}