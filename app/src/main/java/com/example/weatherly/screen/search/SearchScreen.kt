package com.example.weatherly.screen.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherly.navigation.WeatherlyScreens

@Composable
fun SearchScreen(navController: NavController, dataMode: String) {
   Column (
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.Top,
       modifier = Modifier
           .fillMaxSize()
           .padding(top = 50.dp)
   ) {
       Row (
           modifier = Modifier,
           verticalAlignment = Alignment.CenterVertically
       ) {
           Icon(
               imageVector = Icons.AutoMirrored.Default.ArrowBack,
               contentDescription = "Back to main screen icon button",
               modifier = Modifier
                   .clickable {
                       navController.popBackStack()
                   }
                   .padding(5.dp)
           )
           Text(
               text = "Search",
               fontSize = 20.sp,
               modifier = Modifier
                   .padding(5.dp)
           )
           SearchBar(
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(16.dp)
                   .align(Alignment.CenterVertically),
               onSearch = {
                   navController.navigate(WeatherlyScreens.MainScreen.name+"/$it/$dataMode")
               }
           )
       }
   }
}

@Composable
fun SearchBar(
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val searchQueryState = rememberSaveable {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(searchQueryState.value) {
        searchQueryState.value.trim().isNotEmpty()
    }
    Column {
        CommonTextField(
            valueState = searchQueryState,
            placeholder = "Shillong",
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            },

        )
    }
}

@Composable
fun CommonTextField(
    valueState: MutableState<String>,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        placeholder = {
            Text(text = placeholder)
        },
        label = {
            Text(text = "Whats on your mind?")
        },
        keyboardActions = onAction,
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color(255, 193, 7, 255),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier
            .padding(10.dp)
    )
}