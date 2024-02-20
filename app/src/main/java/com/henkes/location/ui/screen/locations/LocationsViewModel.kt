package com.henkes.location.ui.screen.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henkes.location.data.dao.LocationDao
import com.henkes.location.di.Dispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val locationDao: LocationDao,
    @Dispatcher private val coroutineDispatcher: CoroutineDispatcher,
) : ViewModel() {

    val locations = locationDao.getAll().distinctUntilChanged()

    fun clear() {
        viewModelScope.launch(coroutineDispatcher) {
            locationDao.deleteAll()
        }
    }

}
