package com.erkan.simplemovieapp.presentation.fragments

import android.transition.TransitionInflater
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.erkan.simplemovieapp.R
import com.erkan.simplemovieapp.databinding.FragmentMovieDetailsBinding
import com.erkan.simplemovieapp.ext.loadImageWithGlide
import com.erkan.simplemovieapp.presentation.base.BaseFragment
import com.erkan.simplemovieapp.presentation.models.FavoriteMovieUI
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment :
    BaseFragment<MovieViewModel, FragmentMovieDetailsBinding>(R.layout.fragment_movie_details) {
    override val viewModel by viewModel<MovieViewModel>()
    override val binding by viewBinding(FragmentMovieDetailsBinding::bind)
    private val args by navArgs<MovieDetailsFragmentArgs>()
    private var toggle = false

    override fun initialize() {
        initTransition()
        bindViews()
    }

    private fun initTransition() {
        binding.imagePoster.transitionName = args.movie.posterPath
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    private fun bindViews() = with(binding) {
        textTitle.text = args.movie.title
        textOverview.text = args.movie.overview
        imagePoster.loadImageWithGlide(args.movie.posterPath)
        imageBackdrop.loadImageWithGlide(args.movie.posterPath)
        chipYear.text = args.movie.releaseDate
        chipRating.text = args.movie.voteAverage.toString().take(3)
        textLanguage.text = args.movie.originalLanguage
        textOriginalTitle.text = args.movie.originalTitle
        textVoteCount.text = args.movie.voteCount.toString()
    }

    override fun initListeners() {
        checkIfMovieIsFavorite()
        toggleFavorite()
        navigateBack()
    }

    private fun navigateBack() {
        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun checkIfMovieIsFavorite() {
        viewModel.checkIsFavoriteMovie(args.movie.id)
    }

    private fun toggleFavorite() {
        binding.buttonSave.setOnClickListener {
            if (!toggle) {
                viewModel.insertFavoriteMovie(FavoriteMovieUI(
                    args.movie.id,
                    args.movie.title,
                    args.movie.posterPath,
                    args.movie.overview,
                    args.movie.releaseDate,
                    args.movie.voteAverage,
                    args.movie.originalTitle,
                    args.movie.originalLanguage,
                    args.movie.voteCount
                ))
                toggle = true
            } else {
                viewModel.deleteFavoriteMovie(FavoriteMovieUI(
                    args.movie.id,
                    args.movie.title,
                    args.movie.posterPath,
                    args.movie.overview,
                    args.movie.releaseDate,
                    args.movie.voteAverage,
                    args.movie.originalTitle,
                    args.movie.originalLanguage,
                    args.movie.voteCount
                ))
            }
        }
    }

    override fun observeRequest() {
        checkIsFavorite()
        insertState()
        deleteState()
    }

    private fun checkIsFavorite() {
        viewModel.isFavoriteState.spectateUiState(
            success = {
                toggle = it
                if (it) {
                    binding.buttonSave.setIconResource(R.drawable.bookmark)
                } else {
                    binding.buttonSave.setIconResource(R.drawable.bookmark_border)
                }
            }, error = {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun insertState() {
        viewModel.insertState.spectateUiState(
            success = {
                Toast.makeText(requireContext(), "Movie added to favorites", Toast.LENGTH_SHORT)
                    .show()
                binding.buttonSave.setIconResource(R.drawable.bookmark)
            },
            error = {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                binding.buttonSave.setIconResource(R.drawable.bookmark_border)
            }
        )
    }

    private fun deleteState() {
        viewModel.deleteState.spectateUiState(
            success = {
                Toast.makeText(requireContext(), "Movie removed from favorites", Toast.LENGTH_SHORT)
                    .show()
                binding.buttonSave.setIconResource(R.drawable.bookmark_border)
            },
            error = {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                binding.buttonSave.setIconResource(R.drawable.bookmark)
            }
        )
    }
}