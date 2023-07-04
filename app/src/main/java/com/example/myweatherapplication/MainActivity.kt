package com.example.myweatherapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myweatherapplication.ui.theme.MyWeatherApplicationTheme
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyWeatherApplicationTheme {
                WeatherApp()
            }
        }
    }
}

@Composable
fun WeatherApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Start") {
        composable("Start") {
            Greeting(
                name = "Class",
                navController = navController
            )
        }
        composable("Forecast") {
            ForecastScreen()
        }
    }
}

@Composable
fun ForecastScreen() {
    val forecastItems = remember { generateForecastItems() }

    LazyColumn {
        items(forecastItems) { forecast ->
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.day_icon), // Replace with your icon resource
                    contentDescription = stringResource(R.string.icon),
                    modifier = Modifier.size(40.dp)
                )
                Column(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(text = "Date: ${forecast.date}")
                    Text(text = "Temp: ${forecast.temp.day}°C")
                    Text(text = "High: ${forecast.temp.max}°C")
                    Text(text = "Low: ${forecast.temp.min}°C")
                    Text(text = "Sunrise: ${forecast.sunrise}")
                    Text(text = "Sunset: ${forecast.sunset}")
                }
            }
        }
    }
}

private fun generateForecastItems(): List<DayForecast> {
    val forecastItems = mutableListOf<DayForecast>()
    val dateFormatter = DateTimeFormatter.ofPattern("MMM d")
    val timeFormatter = DateTimeFormatter.ofPattern("h:mma")

    for (i in 0 until 16) {
        // Get the UTC timestamps using a UTC calculator
        val date = LocalDate.now().plusDays(i.toLong())
        val sunrise = LocalDateTime.of(date, LocalTime.of(i + 1, 0)).toEpochSecond(ZoneOffset.UTC)
        val sunset = LocalDateTime.of(date, LocalTime.of(i + 2, 0)).toEpochSecond(ZoneOffset.UTC)

        val temp = ForecastTemp(i * 10.0f, i * 5.0f, i * 15.0f)
        val forecast = DayForecast(
            date = date.format(dateFormatter),
            sunrise = LocalDateTime.ofEpochSecond(sunrise, 0, ZoneOffset.UTC).format(timeFormatter),
            sunset = LocalDateTime.ofEpochSecond(sunset, 0, ZoneOffset.UTC).format(timeFormatter),
            temp = temp,
            precipitation = i * 1.2f,
            humidity = i * 80
        )
        forecastItems.add(forecast)
    }

    return forecastItems
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Yellow)
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .padding(all = 16.dp),
                onClick = { navController.navigate("Forecast") },
                text = { Text(text = stringResource(R.string.forecast)) },
                icon = {}
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
        ) {
            Text(
                text = stringResource(R.string.city_state),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier.padding(
                    horizontal = 90.dp,
                    vertical = 20.dp
                )
            )
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Text(
                    text = stringResource(R.string.current_temperature),
                    textAlign = TextAlign.Justify,
                    fontWeight = FontWeight.Bold,
                    fontSize = 110.sp,
                    modifier = Modifier.padding(
                        horizontal = 45.dp,
                        vertical = 15.dp
                    )
                )
                Image(
                    painter = painterResource(R.drawable.day_icon),
                    contentDescription = stringResource(R.string.icon),
                    modifier = Modifier
                        .aspectRatio(9f / 10f)
                        .padding(
                            horizontal = 10.dp,
                            vertical = 15.dp
                        )
                )
            }
            Text(
                text = stringResource(R.string.wind_chill),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(
                    horizontal = 50.dp,
                    vertical = 2.dp
                )
            )
            Spacer(modifier = Modifier.size(30.dp))
            Text(
                text = stringResource(R.string.low),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(
                    horizontal = 30.dp,
                    vertical = 2.dp
                )
            )
            Text(
                text = stringResource(R.string.high),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(
                    horizontal = 30.dp,
                    vertical = 2.dp
                )
            )
            Text(
                text = stringResource(R.string.humidity),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(
                    horizontal = 30.dp,
                    vertical = 2.dp
                )
            )
            Text(
                text = stringResource(R.string.pressure),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(
                    horizontal = 30.dp,
                    vertical = 2.dp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyWeatherApplicationTheme {
        val navController = rememberNavController()
        Greeting(
            name = "Class",
            navController = navController
        )
    }
}