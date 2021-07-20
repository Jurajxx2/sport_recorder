package com.example.sportrecorder.screens.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sportrecorder.databinding.ListItemRecordBinding
import com.example.sportrecorder.model.SportRecord

class RecordListAdapter: RecyclerView.Adapter<RecordListAdapter.RecordViewHolder>() {

    private val items = mutableListOf<SportRecord>()

    fun setItems(newItems: List<SportRecord>) {
        val diffResult = DiffUtil.calculateDiff(RecordListDiffUtil(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val binding = ListItemRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class RecordViewHolder(val binding: ListItemRecordBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(record: SportRecord) {
            binding.record = record
        }
    }
}