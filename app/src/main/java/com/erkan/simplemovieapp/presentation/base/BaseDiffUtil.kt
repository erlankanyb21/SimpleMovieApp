package com.erkan.simplemovieapp.presentation.base

import androidx.recyclerview.widget.DiffUtil


abstract class BaseDiffUtil<T : Any>(
    private val itemsTheSame: (oldItem: T, newItem: T) -> Boolean,
    private val contentsTheSame: (oldItem: T, newItem: T) -> Boolean
) : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return itemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return contentsTheSame(oldItem, newItem)
    }
}