package com.example.weatherapi.ui.screens.location

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.weatherapi.data.model.LatLng.MyLatLng
import com.example.weatherapi.util.Const.Companion.permissions
import com.google.accompanist.systemuicontroller.rememberSystemUiController

//
//@Composable
//fun WeatherScreen(
//    currentLocation: MyLatLng,
//    weatherViewModel: WeatherViewModel
//) {
//    var locationRequired by remember { mutableStateOf(false) }
//
//    val weatherData by weatherViewModel.weatherData.collectAsState(initial = ArrayList())
//
//    val launchMultiplePermissions = rememberLauncherForActivityResult(
//        ActivityResultContracts.RequestMultiplePermissions()
//    ) { permissionMap ->
//        val areGranted = permissionMap.values.reduce { accepted, next -> accepted && next }
//
//        if (areGranted) {
//            locationRequired = true
//            // Request location updates
//            weatherViewModel.refreshWeatherData("Your City Name") // Replace with your city
//        }
//    }
//
//    val systemUIController = rememberSystemUiController()
//
//    DisposableEffect(key1 = currentLocation) {
//        systemUIController.isSystemBarsVisible = false
//        onDispose {
//            systemUIController.isSystemBarsVisible = true
//        }
//    }
//
//
//    LazyColumn {
//        item {
//            Text(text = "Current Weather")
//            Spacer(modifier = Modifier.height(16.dp))
//        }
//
//        if (locationRequired) {
//            items(weatherData) { weatherItem ->
//                Column {
//                    Text(text = "Temperature: ${weatherItem.main.temp}")
//                    Text(text = "Description: ${weatherItem.weather[0].description}")
//                    // Add more details as needed
//                    Spacer(modifier = Modifier.height(8.dp))
//                }
//            }
//        } else {
//            item {
//                Text(text = "Location permission not granted.")
//            }
//        }
//    }
//}
@Composable
fun WeatherScreen(
    context: Context,
    currentLocation: MyLatLng,
    weatherViewModel: WeatherViewModel
) {
    var locationRequired by remember { mutableStateOf(false) }

    val systemUIController = rememberSystemUiController()

    val launchMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionMap ->
        val areGranted = permissionMap.values.reduce { accepted, next -> accepted && next }

        if (areGranted) {
            locationRequired = true
            // Request location updates
            weatherViewModel.refreshWeatherData("Your City Name") // Replace with your city
        }
    }

    DisposableEffect(key1 = true) {
        systemUIController.isSystemBarsVisible = false
        onDispose {
            systemUIController.isSystemBarsVisible = true
        }
    }

    // Use LaunchedEffect to request location permissions and fetch weather data
    LaunchedEffect(key1 = currentLocation) {
        // Check for location permissions
//        val context = LocalContext.current
        val arePermissionsGranted = permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

        if (arePermissionsGranted) {
            locationRequired = true
            weatherViewModel.refreshWeatherData("Your City Name") // Replace with your city
        }
        else{
               launchMultiplePermissions.launch(permissions)

        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Current Weather")
        Spacer(modifier = Modifier.height(16.dp))

        if (locationRequired) {
            // Display weather information here using the data from the ViewModel
            val weatherData by weatherViewModel.weatherData.collectAsState()

            weatherData?.let { weatherList ->
                // Iterate through weatherList and display data
                for (weatherItem in weatherList) {
                    Text(text = "Temperature: ${weatherItem.main?.temp}")
                    Text(text = "Description: ${weatherItem.weather?.get(0)?.description}")
                    // Add more details as needed
                }
            }
        } else {
            Text(text = "Location permission not granted.")
        }
    }
}