package com.example.ozeassessment.ui.bindingadapter

import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.ozeassessment.R
import com.example.ozeassessment.models.Item
import com.example.ozeassessment.ui.favorite.FavoritesFragmentDirections
import com.example.ozeassessment.ui.profile.ProfileFragmentDirections

object BindingAdapters {

    @BindingAdapter("onProfileClickListener")
    @JvmStatic
    fun onProfileClickListener(profileRowLayout: ConstraintLayout, item: Item) {
        profileRowLayout.setOnClickListener {
            try {
                val action = ProfileFragmentDirections.actionProfileFragmentToDetailsFragment(item)
                profileRowLayout.findNavController().navigate(action)
            } catch (e: Exception) {
                Log.d("onRecipeClickListener", e.toString())
            }
        }
    }

    @BindingAdapter("onFavoriteClickListener")
    @JvmStatic
    fun onFavoriteClickListener(favoriteRowLayout: ConstraintLayout, item: Item) {
        favoriteRowLayout.setOnClickListener {
            try {
                val action =
                    FavoritesFragmentDirections.actionFavoritesFragmentToDetailsFragment(item)
                favoriteRowLayout.findNavController().navigate(action)
            } catch (e: Exception) {
                Log.d("onRecipeClickListener", e.toString())
            }
        }
    }

    @BindingAdapter("loadImageFromUrl")
    @JvmStatic
    fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
        imageView.load(imageUrl) {
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
            placeholder(R.drawable.ic_error_placeholder)
        }
    }
}