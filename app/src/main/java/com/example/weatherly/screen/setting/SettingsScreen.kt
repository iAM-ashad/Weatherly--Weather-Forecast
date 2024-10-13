package com.example.weatherly.screen.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherly.data.DataOrException
import com.example.weatherly.model.Weather
import com.example.weatherly.navigation.WeatherlyScreens
import com.example.weatherly.screen.main.MainViewModel
import com.example.weatherly.ui.theme.CustomFonts

@Composable
fun SettingsScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    city: String?
) {

    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData(city = city.toString())
    }.value

    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp)
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
            text = WeatherlyScreens.SettingsScreen.name,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(10.dp)
                .weight(1f)
                .wrapContentWidth(Alignment.End)
        )
    }
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {

            Text(
                text = "Select Units of Measurement",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = CustomFonts(GoogleFont("Playfair Display")),
                modifier = Modifier
                    .padding(10.dp)
            )

            UnitChangeButton(buttonText = "Celsius°") {
                run {
                    navController.navigate(WeatherlyScreens.MainScreen.name+"/${weatherData.data!!.city.name}/celsius")
                }
            }
            UnitChangeButton(buttonText = "Fahrenheit°") {
                run {
                    navController.navigate(WeatherlyScreens.MainScreen.name+"/${weatherData.data!!.city.name}/fahrenheit")
                }
            }

        }
}

@Composable
fun UnitChangeButton(
    buttonText: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(25.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(232,89,117,255),
            contentColor = Color(0,0,0)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 20.dp
        ),
        modifier = Modifier
            .padding(10.dp)
    ) {
        Text(
            text = buttonText,
            fontSize = 25.sp,
            modifier = Modifier
                .padding(4.dp)
        )
    }
}