package com.example.myweatherapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.myweatherapplication.ui.theme.MyWeatherApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyWeatherApplicationTheme {
                val navController = rememberNavController() // Create a NavController to manage navigation within the app

                NavHost(navController = navController, startDestination = "Start") { // Set up navigation using NavHost
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
        }
    }
}


@Composable
fun ForecastScreen(viewModel: ForecastViewModel = hiltViewModel()) {

    val forecastData = viewModel.forecastData.observeAsState()

    val dateFormatter = SimpleDateFormat("MMM d", Locale.getDefault()) // Format the date
    val timeFormatter = SimpleDateFormat("h:mma", Locale.getDefault()) // Format the time

    LaunchedEffect(Unit) {
        viewModel.fetchWeatherForecast()
    }

    // Observe the LiveData and check if the data is available
    forecastData.value?.let { forecasts ->
        if (forecasts.isEmpty()) {
            EmptyView()
        } else {
            LazyColumn {
                item {
                    Text(
                        text = "Forecast",
                        fontWeight = FontWeight.Bold,
                        fontSize = 35.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.Cyan)
                            .fillMaxWidth()
                            .padding(12.dp)
                    )
                }
                forecasts.forEach { dayForecast ->
                    // Use dayForecast properties to populate the UI
                    val date = Date(dayForecast.dayTime * 1000)
                    val sunrise = Date(dayForecast.morning.toLong() * 1000)
                    val sunset = Date(dayForecast.evening.toLong() * 1000)

                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(3.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                WeatherConditionIcon(url = dayForecast.iconUrl)
                                Column(modifier = Modifier.padding(2.dp)) {
                                    Text(
                                        text = dateFormatter.format(date), // Display the formatted date
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                                Column {
                                    Text(
                                        text = "Temp: ${dayForecast.dayTemp}°", // Display the day temperature
                                    )
                                    Spacer(modifier = Modifier.size(6.dp))
                                    Text(
                                        text = "High: ${dayForecast.highTemp}°", // Display the maximum temperature
                                    )
                                }
                                Text(
                                    text = "Low: ${dayForecast.lowTemp}°",  // Display the minimum temperature
                                    modifier = Modifier.padding(2.dp, 28.dp, 1.dp)
                                )
                                Column {
                                    Text(
                                        text = "Sunrise: ${timeFormatter.format(sunrise)}", // Display the sunrise time
                                    )
                                    Spacer(modifier = Modifier.size(6.dp))
                                    Text(
                                        text = "Sunset: ${timeFormatter.format(sunset)}", // Display the sunset time
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun EmptyView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No forecast data available",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun WeatherConditionIcon(
    url: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(model = url, contentDescription = "", modifier = modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(
    name: String,
    navController: NavHostController,
    currentConditionsViewModel: CurrentConditionsViewModel = hiltViewModel()
) {
    val currentConditions = currentConditionsViewModel.weatherData.observeAsState()

    LaunchedEffect(Unit) {
        currentConditionsViewModel.viewAppeared()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name), // Display the app name as the title
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                    )
                    Modifier.padding(12.dp)
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Cyan) // Set the top app bar color to yellow
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .padding(all = 16.dp),
                onClick = { navController.navigate("Forecast") }, // Navigate to the Forecast screen when the FAB is clicked
                text = { Text(text = stringResource(R.string.forecast)) }, // Display the FAB text
                icon = {}
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
        ) {
            currentConditions?.let { weatherData ->
                Text(
                    text = "${weatherData.value?.name}", // Display the city name
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
                        text = "${weatherData.value?.main?.temp}°", // Display the current temperature text
                        textAlign = TextAlign.Justify,
                        fontWeight = FontWeight.Bold,
                        fontSize = 70.sp,
                        modifier = Modifier.padding(
                            horizontal = 45.dp,
                            vertical = 15.dp
                        )
                    )
                    WeatherConditionIcon(url = weatherData.value?.iconUrl ?: "",
                        modifier = Modifier
                            .aspectRatio(9f / 12f) // Adjust the aspect ratio as needed
                            .size(100.dp) // Adjust the size as needed
                            .padding(
                                horizontal = 5.dp,
                                vertical = 15.dp
                            ))
                }
                Text(
                    text = "Feels like ${weatherData.value?.main?.feelsLike}°", // Display the wind speed text
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
                    text = "Low ${weatherData.value?.main?.tempMin}°", // Display the low temperature text
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(
                        horizontal = 30.dp,
                        vertical = 2.dp
                    )
                )
                Text(
                    text = "High ${weatherData.value?.main?.tempMax}°", // Display the high temperature text
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(
                        horizontal = 30.dp,
                        vertical = 2.dp
                    )
                )
                Text(
                    text = "Humidity ${weatherData.value?.main?.humidity}%", // Display the humidity text
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(
                        horizontal = 30.dp,
                        vertical = 2.dp
                    )
                )
                Text(
                    text = "Pressure ${weatherData.value?.main?.pressure} hPa", // Display the pressure text
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
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyWeatherApplicationTheme {
        val navController = rememberNavController()
        Greeting(
            name = "Class",
            navController = navController // Pass the NavController to the Greeting composable
        )
    }
}