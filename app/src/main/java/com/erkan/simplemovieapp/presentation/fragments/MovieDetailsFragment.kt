package com.erkan.simplemovieapp.presentation.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.erkan.simplemovieapp.R
import com.erkan.simplemovieapp.databinding.FragmentMovieDetailsBinding
import com.erkan.simplemovieapp.ext.loadImageWithGlide
import com.erkan.simplemovieapp.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class MovieDetailsFragment :
    BaseFragment<MovieViewModel, FragmentMovieDetailsBinding>(R.layout.fragment_movie_details) {
    override val viewModel by viewModel<MovieViewModel>()
    override val binding by viewBinding(FragmentMovieDetailsBinding::bind)
    private val args by navArgs<MovieDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val animation = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        postponeEnterTransition(200, TimeUnit.MILLISECONDS)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initialize() {
        setTransition()
        bindViews()
    }

    private fun bindViews() = with(binding) {
        textTitle.text = args.movie.title
        textOverview.text = args.movie.overview
        imagePoster.loadImageWithGlide(args.movie.posterPath)
        imageBackdrop.loadImageWithGlide(args.movie.posterPath)
        chipYear.text = args.movie.releaseDate
        chipRating.text = args.movie.voteAverage.toString()
        textLanguage.text = args.movie.originalLanguage
        textOriginalTitle.text = args.movie.originalTitle
        textVoteCount.text = args.movie.voteCount.toString()
    }

    private fun setTransition() {
        binding.imagePoster.transitionName = args.movie.posterPath

        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        postponeEnterTransition()
        binding.imagePoster.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    override fun initListeners() {
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun establishRequest() {

    }

    override fun observeRequest() {

    }
}