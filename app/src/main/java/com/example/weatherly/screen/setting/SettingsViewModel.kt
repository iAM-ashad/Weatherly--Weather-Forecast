package com.example.weatherly.screen.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherly.model.Favorite
import com.example.weatherly.model.Unit
import com.example.weatherly.repository.DBRepository
import com.example.weatherly.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: DBRepository): ViewModel() {

    private val _unitList = MutableStateFlow<List<Unit>>(emptyList())
    val unitList = _unitList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnits().distinctUntilChanged()
                .collect { listOfUnits->
                    if (listOfUnits.isEmpty()){
                        Log.d("TAG", "Empty Units List")
                    }else {
                        _unitList.value = listOfUnits
                        Log.d("Unitossssss", "${unitList.value}")
                    }
                }
        }
    }

    fun addUnit(unit: Unit) =
        viewModelScope.launch {
            repository.addUnit(unit)
        }
    fun deleteUnit(unit: Unit) =
        viewModelScope.launch {
            repository.deleteUnit(unit)
        }
}