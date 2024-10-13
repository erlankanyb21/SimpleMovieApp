package com.erkan.simplemovieapp.presentation.fragments

import android.view.View
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.erkan.simplemovieapp.R
import com.erkan.simplemovieapp.databinding.FragmentFavoriteMoviesBinding
import com.erkan.simplemovieapp.presentation.adapters.FavoriteMovieAdapter
import com.erkan.simplemovieapp.presentation.base.BaseFragment
import com.erkan.simplemovieapp.presentation.models.FavoriteMovieUI
import com.erkan.simplemovieapp.presentation.models.toMovieResult
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteMoviesFragment :
    BaseFragment<MovieViewModel, FragmentFavoriteMoviesBinding>(R.layout.fragment_favorite_movies) {
    override val viewModel by viewModel<MovieViewModel>()
    override val binding by viewBinding(FragmentFavoriteMoviesBinding::bind)
    private val favoriteMovieAdapter = FavoriteMovieAdapter(
        this::onItemClick
    )

    private fun onItemClick(favoriteMovieUI: FavoriteMovieUI, view: View) {
        val directions =
            FavoriteMoviesFragmentDirections.actionFavoriteMoviesFragmentToSecondFragment(
                favoriteMovieUI.toMovieResult()
            )

        val extras = FragmentNavigatorExtras(
            view to favoriteMovieUI.posterPath
        )

        findNavController().navigate(directions, extras)
    }

    override fun initialize() {
        binding.recyclerFavMovies.adapter = favoriteMovieAdapter
        initTransition()
    }

    private fun initTransition() {
        postponeEnterTransition()
        binding.recyclerFavMovies.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    override fun establishRequest() {
        viewModel.fetchFavoriteMovies()
    }

    override fun observeRequest() {
        viewModel.favoriteMoviesState.spectateUiState(
            loading = {
                binding.loadingProgress.isVisible = true
            },
            success = {
                binding.loadingProgress.isVisible = false
                favoriteMovieAdapter.submitList(it)
            },
            error = {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        )
    }
}