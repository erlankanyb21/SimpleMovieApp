package com.erkan.simplemovieapp.presentation.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.erkan.simplemovieapp.R
import com.erkan.simplemovieapp.databinding.FragmentMoviesBinding
import com.erkan.simplemovieapp.presentation.adapters.MainLoadStateAdapter
import com.erkan.simplemovieapp.presentation.adapters.MovieAdapter
import com.erkan.simplemovieapp.presentation.base.BaseFragment
import com.erkan.simplemovieapp.presentation.models.MoviesUI
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment :
    BaseFragment<MovieViewModel, FragmentMoviesBinding>(R.layout.fragment_movies) {
    override val viewModel by viewModel<MovieViewModel>()
    override val binding by viewBinding(FragmentMoviesBinding::bind)

    private val movieAdapter = MovieAdapter(
        onItemClick = this::onItemClick
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val animation =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initialize() {
        binding.recyclerMovies.adapter = movieAdapter.withLoadStateFooter(
            footer = MainLoadStateAdapter()
        )

        postponeEnterTransition()
        binding.recyclerMovies.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    override fun establishRequest() {
        viewModel.pagingAnime().collectPaging {
            movieAdapter.submitData(it)
        }
    }

    override fun initListeners() {

    }

    private fun onItemClick(movie: MoviesUI.Result, image: View) {
        val directions =
            MoviesFragmentDirections.actionFirstFragmentToSecondFragment(
                movie
            )
        val extras = FragmentNavigatorExtras(
            image to movie.posterPath
        )
        findNavController().navigate(directions, extras)
    }
}