package com.example.ozeassessment.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ozeassessment.R
import com.example.ozeassessment.databinding.ProfileRowLayoutBinding
import com.example.ozeassessment.models.Item

class MyViewHolder(private val binding: ProfileRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Item){
        binding.user = item
        binding.executePendingBindings()
    }
    companion object {
        fun create(parent: ViewGroup): MyViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.profile_row_layout,  parent,false)
            val binding = ProfileRowLayoutBinding.bind(view)
            return MyViewHolder(
                binding
            )
        }
    }
}