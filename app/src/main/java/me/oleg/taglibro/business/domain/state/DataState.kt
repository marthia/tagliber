package me.oleg.taglibro.business.domain.state

sealed class DataState<out R> {

    data class Success<out T>(val data: T): DataState<T>()
    data class Error<out T>(val exception: Exception): DataState<Nothing>()
    object Loading: DataState<Nothing>()
}