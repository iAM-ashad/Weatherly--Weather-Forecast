package com.example.weatherly.screen.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.weatherly.R
import com.example.weatherly.navigation.WeatherlyScreens
import com.example.weatherly.ui.theme.CustomFonts
import com.example.weatherly.ui.theme.WeatherlyTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen (navController: NavController = rememberNavController()) {

    val gradientOne = Brush.linearGradient(listOf(
        Color(108,107,251),
        Color(226,115,150)
    ))
    val gradientTwo = Brush.linearGradient(listOf(
        Color(226,115,150),
        Color(108,107,251),
    ))

    val visible by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = true) {
        delay(2000L)
        navController.navigate(WeatherlyScreens.MainScreen.name)
    }
    AnimatedVisibility(
        visible = visible,
        exit = fadeOut(
            animationSpec = spring(
                dampingRatio = 0.5f,
                stiffness = Spring.StiffnessLow
            )
        )
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .animateEnterExit(
                    exit = slideOutVertically()
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.weather_256),
                contentDescription = "App Icon",
                modifier = Modifier
                    .size(175.dp)
                    .clip(CircleShape)
            )
            Text(
                text = "Weatherly \uD83C\uDF1E",
                style = TextStyle(
                    brush = gradientOne,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = CustomFonts(GoogleFont("Kaushan Script"))
                ),
                modifier = Modifier
                    .padding(15.dp)
            )
            Text(
                text = "Unravel the weather, one forecast at a time!",
                style = TextStyle(
                    brush = gradientTwo,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = CustomFonts(GoogleFont("Kaushan Script"))
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    WeatherlyTheme {
        SplashScreen()
    }
}