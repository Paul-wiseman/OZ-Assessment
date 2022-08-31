package com.example.ozeassessment.ui.favorite.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ozeassessment.data.database.entity.FavoriteUserEntity
import com.example.ozeassessment.databinding.FavoriteUserRowLayoutBinding
import com.example.ozeassessment.util.GenericDiffUtil

class FavoriteUsersAdapter : RecyclerView.Adapter<FavoriteUsersAdapter.MyViewHolder>() {
    private var listener: ClickListener? = null
    private var favoriteUsers = emptyList<FavoriteUserEntity>()

    inner class MyViewHolder(private val binding: FavoriteUserRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FavoriteUserEntity) {
            binding.user = item.item
            binding.ivDelete.setOnClickListener {
                listener?.onClick(item)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            FavoriteUserRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = favoriteUsers[position]
        holder.bind(currentRecipe)

    }

    override fun getItemCount(): Int {
        return favoriteUsers.size
    }

    fun registerClickListener(clickListener: ClickListener) {
        listener = clickListener
    }

    fun setData(newData: List<FavoriteUserEntity>) {
        Log.d("FavoriteUsersAdapter", "setData: -----$newData")
        val recipesDiffUtil = GenericDiffUtil(favoriteUsers, newData)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        favoriteUsers = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }


    interface ClickListener {
        fun onClick(item: FavoriteUserEntity)
    }
}