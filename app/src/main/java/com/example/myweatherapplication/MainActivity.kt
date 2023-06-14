package com.example.myweatherapplication

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myweatherapplication.ui.theme.MyWeatherApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyWeatherApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name ),
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Yellow )            )
        },
        bottomBar = {
            BottomAppBar { /* Bottom app bar content */ }
        }

    ) { contentPadding ->
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
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
            Row (horizontalArrangement = Arrangement.SpaceEvenly){
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
                    painter  = painterResource((R.drawable.day_icon)),
                    contentDescription = stringResource(R.string.icon),
                    modifier = Modifier
                        .aspectRatio(9f / 10f)
                        .padding(
                            horizontal = 10.dp,
                            vertical = 15.dp)
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
            Spacer(
                modifier = Modifier
                    .size(30.dp)
            )
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
            Text(
                text = stringResource(R.string.p),
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
        Greeting("Android")
    }
}