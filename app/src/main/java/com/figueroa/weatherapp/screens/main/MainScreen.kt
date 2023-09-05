package com.figueroa.weatherapp.screens.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.figueroa.weatherapp.data.DataOrException
import com.figueroa.weatherapp.model.Forecastday
import com.figueroa.weatherapp.model.WeatherForecast
import com.figueroa.weatherapp.navigation.WeatherScreens
import com.figueroa.weatherapp.screens.settings.SettingsViewModel
import com.figueroa.weatherapp.utils.formatDate
import com.figueroa.weatherapp.utils.formatDecimals
import com.figueroa.weatherapp.widgets.HumidityWindPressureRow
import com.figueroa.weatherapp.widgets.SunsetSunRiseRow
import com.figueroa.weatherapp.widgets.WeatherAppBar
import com.figueroa.weatherapp.widgets.WeatherDetailRow
import com.figueroa.weatherapp.widgets.WeatherStateImage

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?,
) {
    val weatherData = produceState<DataOrException<WeatherForecast, Boolean, Exception>>(
        initialValue = DataOrException(loading = true),
    ) {
        value = mainViewModel.getWeatherData(city = city.toString())
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
            onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            },
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
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = formatDate(data.forecast.forecastday[0].date_epoch),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp),
        )
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
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
        HumidityWindPressureRow(weather = data)
        Divider()
        SunsetSunRiseRow(weather = data)
        Text(
            text = "This week",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(size = 12.dp),
        ) {
            LazyColumn(modifier = Modifier.padding(2.dp), contentPadding = PaddingValues(1.dp)) {
                items(items = data.forecast.forecastday) { item: Forecastday ->
                    WeatherDetailRow(weatherForecast = item)
                }
            }
        }
    }
}
