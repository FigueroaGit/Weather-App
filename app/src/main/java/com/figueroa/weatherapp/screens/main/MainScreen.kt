package com.figueroa.weatherapp.screens.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.figueroa.weatherapp.data.DataOrException
import com.figueroa.weatherapp.model.WeatherForecast
import com.figueroa.weatherapp.utils.formatDate
import com.figueroa.weatherapp.utils.formatDecimals
import com.figueroa.weatherapp.widgets.WeatherAppBar

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()) {
    val weatherData = produceState<DataOrException<WeatherForecast, Boolean, Exception>>(
        initialValue = DataOrException(loading = true),
    ) {
        value = mainViewModel.getWeatherData(city = "San Andres Tuxtla")
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weatherForecast = weatherData.data!!, navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(weatherForecast: WeatherForecast, navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = weatherForecast.location.name + ", ${weatherForecast.location.country}",
            navController = navController,
        )
    }) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            MainContent(data = weatherForecast)
        }
    }
}

@Composable
fun MainContent(data: WeatherForecast) {
    val imageUrl = "http:${data.forecast.forecastday[0].day.condition.icon}"
    Log.d("Img", data.forecast.forecastday[0].day.condition.icon)
    Column(
        modifier = Modifier.padding(4.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = formatDate(data.current.last_updated_epoch),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp),
        )
        Surface(
            modifier = Modifier.padding(4.dp).size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(
                    text = formatDecimals(data.current.temp_f) + "°",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                )
                Text(text = data.current.condition.text, fontStyle = FontStyle.Italic)
            }
        }
    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current).data(imageUrl).build(),
        contentDescription = "Icon Image",
        modifier = Modifier.size(64.dp),
    )
}
