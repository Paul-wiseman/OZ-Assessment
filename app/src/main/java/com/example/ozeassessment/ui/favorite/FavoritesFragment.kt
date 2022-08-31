package com.example.ozeassessment.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ozeassessment.data.database.entity.FavoriteUserEntity
import com.example.ozeassessment.databinding.FragmentFavoritesBinding
import com.example.ozeassessment.ui.favorite.adapter.FavoriteUsersAdapter
import com.example.ozeassessment.viewModel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(), FavoriteUsersAdapter.ClickListener {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val favoriteUserAdapter by lazy { FavoriteUsersAdapter() }
    private var listOfFavoriteUserEntity = emptyList<FavoriteUserEntity>()

    private val viewModel: FavoritesViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        viewModel.readFavoritesFromDb()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        viewModel.readFavoriteUsersEntity.observe(viewLifecycleOwner) { listOfFavorites ->
            if (listOfFavorites.isNotEmpty()) {
                listOfFavoriteUserEntity = listOfFavorites
                hideShimmerEffect()
                favoriteUserAdapter.setData(listOfFavorites)
            } else {
                listOfFavoriteUserEntity = listOf()
                hideShimmerEffect()
                favoriteUserAdapter.setData(listOf())
            }
        }

        binding.btnClearAll.setOnClickListener {
            deleteAllFavoriteUsers()
        }
    }

    private fun deleteAllFavoriteUsers() {
        viewModel.deleteAllFavoriteUser()
    }

    private fun setupRecyclerView() {
        binding.rvFavorite.adapter = favoriteUserAdapter
        binding.rvFavorite.layoutManager = LinearLayoutManager(requireContext())
        favoriteUserAdapter.registerClickListener(this)
        showShimmerEffect()
    }

    override fun onClick(item: FavoriteUserEntity) {
        viewModel.deleteFavoriteUser(item)
        favoriteUserAdapter.setData(listOfFavoriteUserEntity)
    }

    private fun showShimmerEffect() {
        binding.rvFavorite.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.rvFavorite.hideShimmer()
    }
}