package com.erkan.simplemovieapp.presentation.fragments

import androidx.core.view.doOnPreDraw
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

    private fun onItemClick(movie: MoviesUI.Result) {
        val directions =
            MoviesFragmentDirections.actionFirstFragmentToSecondFragment(
                movie
            )
        findNavController().navigate(directions)
    }
}