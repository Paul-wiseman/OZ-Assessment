package com.example.ozeassessment.ui.profiledetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.ozeassessment.R
import com.example.ozeassessment.data.database.entity.FavoriteUserEntity
import com.example.ozeassessment.databinding.FragmentDetailsBinding
import com.example.ozeassessment.models.Item
import com.example.ozeassessment.viewModel.MainViewModel
import com.example.ozeassessment.util.showSnackBar
import com.example.ozeassessment.viewModel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    private val args by navArgs<DetailsFragmentArgs>()
    private val viewModel: FavoritesViewModel by activityViewModels()

    private var isUserSaved = false
    private var savedUserId = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details,container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.user = args.user
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.readFavoritesFromDb()

        binding.btnAddRemove.setOnClickListener {
            val user = args.user
            val isUserSaved = isUserSaved
            if (isUserSaved){
                removeFromFavorites(user)
            }else{
                saveToFavorites(user)
            }
        }
        checkSavedUser()
    }

    override fun onResume() {
        super.onResume()
        checkSavedUser()
    }

    private fun checkSavedUser() {
        viewModel.readFavoriteUsersEntity.observe(viewLifecycleOwner) { favoritesEntity ->
            try {
                for (savedUser in favoritesEntity) {
                    if (savedUser.item.id == args.user.id) {
                        savedUserId = savedUser.id
                        isUserSaved = true

                        binding.btnAddRemove.text = getString(R.string.removed_from_favorites)
                    } else {
                        binding.btnAddRemove.text =  getString(R.string.added_to_favorites)
                    }
                }
            } catch (e: Exception) {
                Log.d("DetailsActivity", e.message.toString())
            }
        }
    }

    private fun saveToFavorites(item: Item) {
        val favoritesEntity = FavoriteUserEntity(
            0,
                item
            )
        viewModel.saveFavoriteUser(favoritesEntity)
        binding.btnAddRemove.text = getString(R.string.removed_from_favorites)
        binding.root.showSnackBar(getString(R.string.user_saved))
        isUserSaved = true
    }
    private fun removeFromFavorites(item: Item) {
        val favoritesEntity = FavoriteUserEntity(
            savedUserId,
            item
        )
        viewModel.deleteFavoriteUser(favoritesEntity)
        binding.btnAddRemove.text = getString(R.string.added_to_favorites)
        binding.root.showSnackBar(getString(R.string.user_removed))
       isUserSaved = false
    }
}