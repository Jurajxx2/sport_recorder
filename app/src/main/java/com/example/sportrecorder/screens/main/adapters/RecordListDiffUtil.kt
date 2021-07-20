package com.example.sportrecorder.screens.main.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.sportrecorder.model.SportRecord

class RecordListDiffUtil(val oldList: List<SportRecord>, val newList: List<SportRecord>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition] == newList[newItemPosition]
}