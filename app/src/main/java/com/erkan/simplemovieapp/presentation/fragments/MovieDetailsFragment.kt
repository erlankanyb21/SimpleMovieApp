package com.erkan.simplemovieapp.presentation.fragments

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.erkan.simplemovieapp.R
import com.erkan.simplemovieapp.databinding.FragmentMovieDetailsBinding
import com.erkan.simplemovieapp.ext.loadImageWithGlide
import com.erkan.simplemovieapp.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment :
    BaseFragment<MovieViewModel, FragmentMovieDetailsBinding>(R.layout.fragment_movie_details) {
    override val viewModel by viewModel<MovieViewModel>()
    override val binding by viewBinding(FragmentMovieDetailsBinding::bind)
    private val args by navArgs<MovieDetailsFragmentArgs>()

    override fun initialize() {
        bindViews()
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

    }

    override fun establishRequest() {

    }

    override fun observeRequest() {

    }
}