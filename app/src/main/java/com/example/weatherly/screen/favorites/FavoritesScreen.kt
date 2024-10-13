package com.example.weatherly.screen.favorites

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherly.R
import com.example.weatherly.model.Favorite
import com.example.weatherly.navigation.WeatherlyScreens
import com.example.weatherly.ui.theme.CustomFonts

@Composable
fun FavoritesScreen(
    dataMode: String,
    navController: NavController,
    favoritesVM: FavoritesViewModel = hiltViewModel(),
) {

    Column (
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .padding(top = 40.dp)
            .fillMaxSize()
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
                    .padding(10.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = ""
                )
            }
            Text(
                text = WeatherlyScreens.FavoritesScreen.name,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f)
                    .wrapContentWidth(Alignment.End)
            )
        }
        val list = favoritesVM.favList.collectAsState().value
        LazyColumn {
            items(items = list) {
                FavCityCard(
                    favorite = it,
                    dataMode = dataMode,
                    navController = navController,
                    favoritesVM = favoritesVM,
                    weatherImage = painterResource(id = R.drawable.citycardbg)
                )
            }
        }
    }
}

@Composable
fun FavCityCard(
    favorite: Favorite,
    dataMode: String,
    weatherImage: Painter,
    navController: NavController,
    favoritesVM: FavoritesViewModel
) {
    val context = LocalContext.current
    Card (
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        shape = RoundedCornerShape(topEnd = 25.dp, bottomStart = 25.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .size(height = 75.dp, width = 250.dp)
            .clickable {
                navController.navigate(WeatherlyScreens.MainScreen.name + "/${favorite.city}/$dataMode")
            }
    ) {
        Box (
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = weatherImage,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    modifier = Modifier
                        .padding(5.dp)
                        .padding(start = 10.dp)
                ) {
                    Text(
                        text = favorite.city+", ${favorite.country}",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontFamily = CustomFonts(GoogleFont("Oswald"))
                    )
                }
                IconButton(
                    onClick = {
                        favoritesVM.deleteFavorite(favorite)
                        Toast.makeText(context, "${favorite.city} removed from favorites!", Toast.LENGTH_SHORT).show()
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete city from Favorites List"
                    )
                }
            }
        }
    }
}