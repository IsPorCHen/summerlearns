package com.bignerdranch.android.fuck3.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.fuck3.ui.models.Movie

class MoviesViewModel : ViewModel() {
    private val _query = MutableLiveData<String>()

    val query: LiveData<String>
        get() = _query

    fun setQuery(query: String) {
        _query.value = query
    }
}
