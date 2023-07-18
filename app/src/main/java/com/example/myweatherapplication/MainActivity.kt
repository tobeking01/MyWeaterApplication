package com.example.myweatherapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myweatherapplication.ui.theme.MyWeatherApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
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
                WeatherApp()
            }
        }
    }
}

@Composable
fun WeatherApp() {
    val navController = rememberNavController() // Create a NavController to manage navigation within the app

    NavHost(navController = navController, startDestination = "Start") { // Set up navigation using NavHost
        composable("Start") {
            Greeting(
                name = "Class",
                navController = navController // Pass the NavController to the Greeting composable
            )
        }
        composable("Forecast") {
            ForecastScreen()
        }
    }
}

@Composable
fun ForecastScreen() {
    val forecastItems = remember { generateForecastItems() } // Generate forecast items using the generateForecastItems function

    val dateFormatter = SimpleDateFormat("MMM d", Locale.getDefault()) // Format the date
    val timeFormatter = SimpleDateFormat("h:mma", Locale.getDefault()) // Format the time

    LazyColumn {
        item {
            Text(
                text = "Forecast", // Display the forecast title
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Cyan) // Set the background color to yellow
                    .fillMaxWidth()
                    .padding(12.dp)
            )
        }
        items(forecastItems) { forecast ->
            val date = Date(forecast.date) // Convert the forecast's date to a Date object
            val sunrise = Date(forecast.sunrise) // Convert the forecast's sunrise time to a Date object
            val sunset = Date(forecast.sunset) // Convert the forecast's sunset time to a Date object

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
                    Image(
                        painter = painterResource(R.drawable.day_icon), // Display the day icon
                        contentDescription = stringResource(R.string.icon),
                        modifier = Modifier.size(30.dp)
                    )
                    Column(modifier = Modifier.padding(2.dp)) {
                        Text(
                            text = dateFormatter.format(date), // Display the formatted date
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Column {
                        Text(
                            text = "Temp: ${forecast.temp.day}°", // Display the day temperature
                        )
                        Spacer(modifier = Modifier.size(6.dp))
                        Text(
                            text = "High: ${forecast.temp.max}°", // Display the maximum temperature
                        )
                    }
                    Text(
                        text = "Low: ${forecast.temp.min}°", // Display the minimum temperature
                        modifier = Modifier.padding(2.dp,28.dp,1.dp)
                    )
                    Column{
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

private fun generateForecastItems(): List<DayForecast> {
    val forecastItems = mutableListOf<DayForecast>()
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")) // Get an instance of Calendar set to UTC time zone

    for (i in 0 until 16) {
        val date = calendar.time // Get the current date from the calendar
        val sunrise = calendar.apply { add(Calendar.HOUR_OF_DAY, i + 1) }.time // Add i + 1 hours to the calendar and get the sunrise time
        val sunset = calendar.apply { add(Calendar.HOUR_OF_DAY, 11) }.time // Add 11 hours to the calendar and get the sunset time

        val temp = ForecastTemp(i * 10.0f, i * 5.0f, i * 15.0f, i * 2.0f, i * 3.0f, i * 4.0f) // Create a ForecastTemp object with calculated temperature values
        val forecast = DayForecast(
            date = date.time,
            sunrise = sunrise.time,
            sunset = sunset.time,
            temp = temp,
            precipitation = i * 1.2f, // Calculate the precipitation value based on the loop variable
            humidity = i * 80 // Calculate the humidity value based on the loop variable
        )
        forecastItems.add(forecast) // Add the forecast object to the forecastItems list
    }

    return forecastItems // Return the generated forecast items list
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(
    name: String,
    navController: NavHostController,
    currentConditionsViewModel: CurrentConditionsViewModel = hiltViewModel()
) {
    val currentConditions by currentConditionsViewModel.weatherData.observeAsState()

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
                    text = "${weatherData.name}", // Display the city name
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
                        text = "${weatherData.main.temp}°", // Display the current temperature text
                        textAlign = TextAlign.Justify,
                        fontWeight = FontWeight.Bold,
                        fontSize = 80.sp,
                        modifier = Modifier.padding(
                            horizontal = 45.dp,
                            vertical = 15.dp
                        )
                    )
                    Image(
                        painter = painterResource(R.drawable.day_icon), // Display the day icon
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
                    text = "Feels like ${weatherData.main.feelsLike}°", // Display the wind speed text
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
                    text = "Low ${weatherData.main.tempMin}°", // Display the low temperature text
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(
                        horizontal = 30.dp,
                        vertical = 2.dp
                    )
                )
                Text(
                    text = "High ${weatherData.main.tempMax}°", // Display the high temperature text
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(
                        horizontal = 30.dp,
                        vertical = 2.dp
                    )
                )
                Text(
                    text = "Humidity ${weatherData.main.humidity}%", // Display the humidity text
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(
                        horizontal = 30.dp,
                        vertical = 2.dp
                    )
                )
                Text(
                    text = "Pressure ${weatherData.main.pressure} hPa", // Display the pressure text
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