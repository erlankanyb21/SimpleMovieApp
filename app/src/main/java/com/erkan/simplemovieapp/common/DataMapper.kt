package com.erkan.simplemovieapp.common

interface DataMapper<T> {
    fun toDomain(): T
}