package com.figueroa.weatherapp.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.figueroa.weatherapp.R
import com.figueroa.weatherapp.model.Forecastday
import com.figueroa.weatherapp.model.WeatherForecast
import com.figueroa.weatherapp.utils.formatDate
import com.figueroa.weatherapp.utils.formatDecimals

@Composable
fun WeatherDetailRow(weatherForecast: Forecastday) {
    val imageUrl = "http:${weatherForecast.day.condition.icon}"
    Surface(
        modifier = Modifier.padding(3.dp).fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(8.dp)),
        color = Color.White,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = formatDate(weatherForecast.date_epoch).split(",")[0],
                modifier = Modifier.padding(start = 5.dp),
            )
            WeatherStateImage(imageUrl = imageUrl)
            Surface(
                modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400),
            ) {
                Text(
                    text = weatherForecast.day.condition.text,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue.copy(alpha = 0.7F),
                            fontWeight = FontWeight.SemiBold,
                        ),
                    ) {
                        append(formatDecimals(weatherForecast.day.maxtemp_f) + "°")
                    }
                    withStyle(style = SpanStyle(color = Color.LightGray)) {
                        append(formatDecimals(weatherForecast.day.mintemp_f) + "°")
                    }
                },
            )
        }
//
    }
}

@Composable
fun SunsetSunRiseRow(weather: WeatherForecast) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 15.dp, bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "Sunrise",
                modifier = Modifier.size(32.dp),
            )
            Text(
                text = weather.forecast.forecastday[0].astro.sunrise,
                style = MaterialTheme.typography.labelLarge,
            )
        }
        Row {
            Text(
                text = weather.forecast.forecastday[0].astro.sunset,
                style = MaterialTheme.typography.labelLarge,
            )
            Image(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "Sunrise",
                modifier = Modifier.size(32.dp),
            )
        }
    }
}

@Composable
fun HumidityWindPressureRow(weather: WeatherForecast) {
    Row(
        modifier = Modifier.padding(12.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "Humidity Icon",
                modifier = Modifier.size(20.dp),
            )
            Text(
                text = "${weather.current.humidity}%",
                style = MaterialTheme.typography.labelLarge,
            )
        }
        Row() {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "Pressure Icon",
                modifier = Modifier.size(20.dp),
            )
            Text(
                text = "${weather.current.pressure_mb} psi",
                style = MaterialTheme.typography.labelLarge,
            )
        }
        Row() {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "Wind Icon",
                modifier = Modifier.size(20.dp),
            )
            Text(
                text = "${weather.current.wind_mph} mph",
                style = MaterialTheme.typography.labelLarge,
            )
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
