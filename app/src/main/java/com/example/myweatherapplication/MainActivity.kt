package com.example.myweatherapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val padding = 16.dp
    Column(
        Modifier
//            .padding(5.dp,6.dp,6.dp,6.dp)
            .padding(padding)
            .fillMaxWidth()
            .background(color = Color.Gray)


//            .size(width = 6.dp, height = 6.dp)
//        verticalArrangement = Arrangement.Top,

    ){
        Text(text = stringResource(R.string.app_name ))
    }
//    Column(Modifier.fillMaxSize(),){
//        Text(text = stringResource(R.string.city_state),
//            Modifier.background(color = Color.Cyan),
//            fontSize = 22.sp)
//        Spacer(Modifier.size(10.dp))
//        Text(text = stringResource(R.string.current_temperature))
//        Spacer(Modifier.size(10.dp))
//    }

//    Column (verticalArrangement = Arrangement.Center){
//        // display low
//        Text(text = stringResource(R.string.low ))
//        // display high
//        Text(text = stringResource(R.string.high ))
//        // display humidity
//        Text(text = stringResource(R.string.humidity ))
//        // display pressure
//        Text(text = stringResource(R.string.pressure ))
//    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyWeatherApplicationTheme {
        Greeting("Android")
    }
}