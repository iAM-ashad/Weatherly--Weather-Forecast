package com.example.weatherly.screen.main

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherly.R
import com.example.weatherly.data.DataOrException
import com.example.weatherly.model.Favorite
import com.example.weatherly.model.Weather
import com.example.weatherly.screen.favorites.FavoritesViewModel
import com.example.weatherly.ui.theme.CustomFonts
import com.example.weatherly.util.fahrenheitToCelsius
import com.example.weatherly.util.formatDate
import com.example.weatherly.util.formatTime
import com.example.weatherly.widgets.WeatherlyTopBar

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    city: String?,
    dataMode: String
) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData(city = city.toString())
    }.value

    if (weatherData.loading == true) {
        Log.d("Loading", "LOADING!!!")
        CircularProgressIndicator()
    }else if (weatherData.data != null) {
        Log.d("Loaded", "LOADED")
        MainScaffold(weather = weatherData.data!!, navController = navController, dataMode = dataMode)
    }
}

@Composable
fun MainScaffold(
    weather: Weather,
    dataMode: String?,
    navController: NavController,
    favoritesVM: FavoritesViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            WeatherlyTopBar(
                title = weather.city.name + ", "+ weather.city.country,
                isMainScreen = true,
                navController = navController,
                onAddActionButtonClicked = {
                    favoritesVM
                        .addFavorite(
                            Favorite(
                                weather.city.name,
                                weather.city.country
                            ))
                    Toast.makeText(context, "${weather.city.name} added to favorites!", Toast.LENGTH_SHORT).show()
                },
                city = weather.city.name,
                dataMode = dataMode.toString()
            )
        },
        modifier = Modifier
            .padding(top = 30.dp)
    ) { innerPadding->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            MainContent(
                data = weather,
                dataMode = dataMode.toString()
            )
        }
    }
}

@Composable
fun MainContent(
    data: Weather,
    dataMode: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(painter = painterResource(
            id = R.drawable.bg_2),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            Card (
                elevation = CardDefaults.cardElevation(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(232,89,117,255),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Text(
                    text = formatDate(data.list[0].dt),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(3.dp)
                        .padding(horizontal = 5.dp)
                )
            }
            Surface (
                shape = CircleShape,
                color = Color(255, 236, 81, 255),
                modifier = Modifier
                    .size(200.dp)
                    .padding(4.dp)
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                ) {
                    when (data.list[0].weather[0].main) {
                        "Rain" -> {
                            Icon(
                                painter = painterResource(R.drawable.rainy),
                                contentDescription = "",
                                Modifier
                                    .size(90.dp)
                                    .padding(20.dp)
                            )
                        }
                        "Clear" -> {
                            Icon(
                                painter = painterResource(R.drawable.sun),
                                contentDescription = "",
                                Modifier
                                    .size(90.dp)
                                    .padding(20.dp)
                            )
                        }
                        "Thunderstorm" -> {
                            Icon(
                                painter = painterResource(R.drawable.storm),
                                contentDescription = "",
                                Modifier
                                    .size(90.dp)
                                    .padding(20.dp)
                            )
                        }
                        "Clouds" -> {
                            Icon(
                                painter = painterResource(R.drawable.cloudy),
                                contentDescription = "",
                                Modifier
                                    .size(90.dp)
                                    .padding(20.dp)
                            )
                        }
                        else -> Icon(
                            painter = painterResource(R.drawable.sun),
                            contentDescription = "",
                            Modifier
                                .size(90.dp)
                                .padding(20.dp)
                        )
                    }
                    Text(
                        text = when (dataMode) {
                            "celsius" -> fahrenheitToCelsius(data.list[0].temp.day).toString()+"°"
                            else -> data.list[0].temp.day.toString()+"°"
                        },
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(5.dp)
                            .padding(start = 8.dp)
                    )
                    Text(
                        text = data.list[0].weather[0].main,
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Row (
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Column (
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 10.dp)
                ) {
                    Text(
                        text = "Its",
                        fontSize = 40.sp,
                        fontFamily = CustomFonts(GoogleFont("Caveat")),
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .padding(2.dp)
                            .padding(start = 60.dp)
                    )
                    Text(
                        text = data.list[0].weather[0].main,
                        fontSize = 90.sp,
                        fontFamily = CustomFonts(GoogleFont("Dancing Script")),
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .padding(2.dp)
                            .padding(start = 10.dp)
                    )
                    Text(
                        text = "Out There",
                        fontSize = 50.sp,
                        fontFamily = CustomFonts(GoogleFont("Caveat")),
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .padding(2.dp)
                    )
                }
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 10.dp)
                ) {
                    ExtraInfo(
                        icon = painterResource(id = R.drawable.humidity),
                        description = "Humidity",
                        text = data.list[0].humidity.toString()+"°",
                        textSize = 27.sp,
                        captionSize = 23.sp,
                        fontType = CustomFonts(GoogleFont("Satisfy")),
                        modifier = Modifier
                    )
                    ExtraInfo(
                        icon = painterResource(id = R.drawable.pneumatic),
                        description = "Pressure",
                        text = data.list[0].pressure.toString()+" psi",
                        textSize = 27.sp,
                        captionSize = 23.sp,
                        fontType = CustomFonts(GoogleFont("Satisfy")),
                        modifier = Modifier

                    )
                    ExtraInfo(
                        icon = painterResource(id = R.drawable.gust),
                        description = "Gust",
                        text = data.list[0].gust.toInt().toString()+" mph",
                        textSize = 23.sp,
                        captionSize = 20.sp,
                        fontType = CustomFonts(GoogleFont("Satisfy")),
                        modifier = Modifier
                    )
                }
            }
            Card (
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 30.dp
                ),
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier
                    .padding(
                        top = 20.dp,
                        start = 10.dp,
                        end = 10.dp,
                        bottom = 20.dp
                    )
                    .size(height = 250.dp, width = 400.dp)

            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bg),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column (
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize()
                                //.padding(top = 10.dp, start = 10.dp)
                        ){
                            Text(
                                text = "City",
                                fontSize = 50.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontFamily = CustomFonts(GoogleFont("Rancho")),
                                modifier = Modifier
                                    .padding(start = 20.dp)
                            )
                            Text(
                                text = "of",
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontFamily = CustomFonts(GoogleFont("Rancho")),
                                modifier = Modifier
                                    .padding(start = 40.dp)
                            )
                            Text(
                                text = data.city.name,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontFamily = CustomFonts(GoogleFont("Rancho")),
                                modifier = Modifier
                                    .padding(start = 60.dp)
                            )

                        }
                        Column (
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(end = 10.dp)
                                .weight(1f)
                        ) {
                            ExtraInfo(
                                icon = painterResource(id = R.drawable.sunset__1_),
                                description = "Sunrise",
                                text = formatTime(data.list[0].sunrise),
                                textSize = 30.sp,
                                captionSize = 15.sp,
                                fontType = CustomFonts(GoogleFont("Rancho")),
                                modifier = Modifier
                                    .padding(top = 10.dp, bottom = 5.dp)
                                    .weight(1f)
                            )
                            ExtraInfo(
                                icon = painterResource(id = R.drawable.sunset__1_),
                                description = "Sunrise",
                                text = formatTime(data.list[0].sunset),
                                textSize = 30.sp,
                                captionSize = 15.sp,
                                fontType = CustomFonts(GoogleFont("Rancho")),
                                modifier = Modifier
                                    .padding(top = 5.dp, end = 10.dp, bottom = 10.dp)
                                    .weight(1f)
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun ExtraInfo(
    icon: Painter,
    textSize: TextUnit,
    fontType: FontFamily,
    captionSize: TextUnit,
    description: String,
    text: String,
    modifier: Modifier
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(5.dp)
    ) {
        Image(
            painter = icon,
            contentDescription = description,
            modifier = Modifier
                .size(45.dp)
                .padding(end = 4.dp)
        )
        Column (
            modifier = Modifier
        ){
            Text(
                text = description,
                fontSize = textSize,
                fontFamily = fontType,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .padding(start = 2.dp)
            )
            Text(
                text = text,
                fontSize = captionSize,
                fontFamily = fontType,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .padding(start = 2.dp)
            )
        }
    }
}