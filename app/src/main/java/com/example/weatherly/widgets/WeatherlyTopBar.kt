package com.example.weatherly.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherly.navigation.WeatherlyScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherlyTopBar (
    dataMode: String,
    city: String,
    title: String,
    icon: ImageVector? = null,
    isMainScreen: Boolean,
    navController: NavController,
    onAddActionButtonClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    val isExpanded = remember {
        mutableStateOf(false)
    }
    if (isExpanded.value) {
        DropDown(showDialog = isExpanded, navController = navController, city = city, dataMode = dataMode)
    }
    CenterAlignedTopAppBar(
        title = {
        Text(
            text = title,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            color = Color(0, 0, 0, 255),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .padding(start = 10.dp)
        )
    },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.Transparent
        ),
        actions = {
            if (isMainScreen) {
                IconButton(onClick = {
                    navController.navigate(WeatherlyScreens.SearchScreen.name+"/$dataMode")
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search for a different city",
                        modifier = Modifier
                            .padding(4.dp)
                    )
                }
                IconButton(
                    onClick = { isExpanded.value = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More Options",
                        modifier = Modifier
                            .padding(4.dp)
                    )
                }
                IconButton(onClick = {
                    onAddActionButtonClicked.invoke()
                }) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Add city to favorites",
                        modifier = Modifier
                            .padding(4.dp)
                    )
                }
            }
            else Box{}
        },
        navigationIcon = {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Navigation Button",
                    modifier = Modifier
                        .clickable {
                            onButtonClicked.invoke()
                        }
                        .padding(3.dp)
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .shadow(5.dp)
            .wrapContentHeight(Alignment.CenterVertically)
    )
}

@Composable
fun DropDown(
    dataMode: String,
    showDialog: MutableState<Boolean>,
    city: String,
    navController: NavController
) {

    val items = listOf("About", "Favorites", "Settings")
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(
                top = 45.dp,
                right = 20.dp
            )
    ) {
        DropdownMenu(
            expanded = showDialog.value,
            onDismissRequest = { showDialog.value = false },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        ) {
            items.forEachIndexed{ index, text->
            DropdownMenuItem(
                text = { 
                    Row (
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Icon(
                            imageVector = when (text) {
                                "About"-> Icons.Default.Info
                                "Favorites"-> Icons.Default.Favorite
                                else-> Icons.Default.Settings
                            },
                            contentDescription = "",
                            tint = Color.LightGray,
                            modifier = Modifier
                                .padding(5.dp)
                        )
                        Text(
                            text = text,
                            modifier = Modifier
                                .padding(5.dp)
                        )
                    }
                },
                onClick = {
                    showDialog.value = false
                    when (text) {
                        "About" -> navController.navigate(WeatherlyScreens.AboutScreen.name)
                        "Favorites" -> navController.navigate(WeatherlyScreens.FavoritesScreen.name+"/$dataMode")
                        else -> navController.navigate(WeatherlyScreens.SettingsScreen.name+"/$city")
                    }
                }
            )
        }
    }
}
}
