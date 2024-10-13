package com.example.weatherly.screen.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherly.model.Favorite
import com.example.weatherly.repository.DBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: DBRepository): ViewModel() {

    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged()
                .collect { listOfFav->
                    if (listOfFav.isEmpty()){
                        Log.d("TAG", "Empty Favorites List")
                    }else {
                        _favList.value = listOfFav
                        Log.d("Favoritosssss", "${favList.value}")
                    }
                }
        }
    }

    fun addFavorite(favorite: Favorite) =
        viewModelScope.launch {
            repository.addFavorite(favorite)
        }
    fun deleteFavorite(favorite: Favorite) =
        viewModelScope.launch {
            repository.deleteFavorite(favorite)
        }
    fun getFavByID(city: String) =
        viewModelScope.launch {
            repository.getFavByID(city)
        }

}