package com.erkan.simplemovieapp.presentation.adapters

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.erkan.simplemovieapp.R
import com.erkan.simplemovieapp.databinding.ItemMovieBinding
import com.erkan.simplemovieapp.ext.toFullImageUrl
import com.erkan.simplemovieapp.presentation.base.BaseDiffUtil
import com.erkan.simplemovieapp.presentation.models.FavoriteMovieUI

class FavoriteMovieAdapter(
    private val onItemClick: (FavoriteMovieUI, View) -> Unit,
) : ListAdapter<FavoriteMovieUI, FavoriteMovieAdapter.ViewHolder>(CarouselDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    inner class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: FavoriteMovieUI) {
            bindingItems(data)
            checkForOrientation(data)
            binding.imageMovie.transitionName = data.posterPath
            itemView.setOnClickListener {
                onItemClick(data, binding.imageMovie)
            }
        }

        private fun bindingItems(data: FavoriteMovieUI) = with(binding) {
            imageMovie.load(data.posterPath.toFullImageUrl()) {
                listener { _, _ ->
                    progressBar.isVisible = false
                }
                crossfade(true)
                crossfade(800)
                placeholder(R.drawable.placeholder_movie)
                error(R.drawable.ic_error)
                scale(coil.size.Scale.FILL)
            }
            textYear.text = data.releaseDate
            textTitle.text = data.title
            chipRating.text = data.voteAverage.toString().take(3)
        }

        private fun checkForOrientation(data: FavoriteMovieUI) {
            val orientation = binding.root.resources.configuration.orientation

            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                binding.textDesc?.text = data.overview
            } else {
                binding.textDesc?.isVisible = false
            }
        }
    }

    object CarouselDiffCallback : BaseDiffUtil<FavoriteMovieUI>(
        itemsTheSame = { oldItem, newItem -> oldItem == newItem },
        contentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
}