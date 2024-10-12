package com.erkan.simplemovieapp.presentation.adapters

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.erkan.simplemovieapp.databinding.ItemMovieBinding
import com.erkan.simplemovieapp.ext.loadImageWithGlide
import com.erkan.simplemovieapp.ext.toFullImageUrl
import com.erkan.simplemovieapp.presentation.base.BaseDiffUtil
import com.erkan.simplemovieapp.presentation.models.MoviesUI

class MovieAdapter(
    private val onItemClick: (MoviesUI.Result, View) -> Unit,
) :
    PagingDataAdapter<MoviesUI.Result, MovieAdapter.AnimeViewHolder>(Companion) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AnimeViewHolder(
        ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    inner class AnimeViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: MoviesUI.Result) {
            bindingItems(data)
            checkForOrientation(data)
            binding.imageMovie.transitionName = data.posterPath
            itemView.setOnClickListener {
                onItemClick(data, binding.imageMovie)
            }
        }

        private fun bindingItems(data: MoviesUI.Result) = with(binding) {
            imageMovie.loadImageWithGlide(data.posterPath)
            textYear.text = data.releaseDate
            textTitle.text = data.title
            chipRating.text = data.voteAverage.toString().take(3)
        }

        private fun checkForOrientation(data: MoviesUI.Result) {
            val orientation = binding.root.resources.configuration.orientation

            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                binding.textDesc?.text = data.overview
            } else {
                binding.textDesc?.isVisible = false
            }
        }
    }

    companion object : BaseDiffUtil<MoviesUI.Result>(
        itemsTheSame = { oldItem, newItem ->
            oldItem.id == newItem.id
        },
        contentsTheSame = { oldItem, newItem ->
            oldItem == newItem
        }
    )
}