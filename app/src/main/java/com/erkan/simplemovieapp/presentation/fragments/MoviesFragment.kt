package com.erkan.simplemovieapp.presentation.fragments

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.erkan.simplemovieapp.R
import com.erkan.simplemovieapp.databinding.FragmentMoviesBinding
import com.erkan.simplemovieapp.presentation.adapters.MainLoadStateAdapter
import com.erkan.simplemovieapp.presentation.adapters.MovieAdapter
import com.erkan.simplemovieapp.presentation.base.BaseFragment
import com.erkan.simplemovieapp.presentation.models.MoviesUI
import kotlinx.coroutines.flow.collectLatest
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

        binding.swipeRefresh.setOnRefreshListener {
            movieAdapter.refresh()
        }
    }

    override fun establishRequest() {
        viewModel.pagingMovies().collectPaging {
            movieAdapter.submitData(it)
        }

        collectFlowWithLifeCycleAwareness {
            movieAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.swipeRefresh.isRefreshing = loadStates.refresh is LoadState.Loading
                binding.loadingProgress.isVisible = loadStates.refresh is LoadState.Loading
            }
        }
    }

    private fun onItemClick(movie: MoviesUI.Result) {
        val directions =
            MoviesFragmentDirections.actionFirstFragmentToSecondFragment(
                movie
            )
        findNavController().navigate(directions)
    }
}