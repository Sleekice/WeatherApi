package com.example.weatherapi.ui

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.weatherapi.data.model.LatLng.MyLatLng
import com.example.weatherapi.ui.theme.WeatherApiTheme
import com.example.weatherapi.util.Const.Companion.permissions
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import android.content.Context
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.weatherapi.ui.screens.location.WeatherScreen
import com.example.weatherapi.ui.screens.location.WeatherViewModel
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var locationRequired: Boolean = false

    private val weatherViewModel: WeatherViewModel by viewModels() // Use by viewModels()

//    @Inject
//    lateinit var weatherViewModel: WeatherViewModel

    override fun onResume() {
        super.onResume()
        if(locationRequired) startLocationUpdate()
    }


    override fun onPause() {
        super.onPause()

        locationCallback?.let {
            fusedLocationProviderClient?.removeLocationUpdates(it)
        }
    }


    @SuppressLint("MissingPermission")
    fun startLocationUpdate() {
        locationCallback?.let {
            val locationRequest = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,100
            )
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(3000)
                .setMaxUpdateDelayMillis(100)
                .build()

            fusedLocationProviderClient?.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLocationClient()



        setContent {

            //This keeps the value of our current locaiton.
            var currentLocation by remember {
                mutableStateOf(MyLatLng(0.0,0.0))
            }

            locationCallback = object: LocationCallback(){
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)

                    for(location in p0.locations){
                        currentLocation = MyLatLng(
                            location.latitude,
                            location.longitude
                        )
                    }
                }
            }


            WeatherApiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    WeatherScreen(this,currentLocation, weatherViewModel)
                   // LocationScreen(this@MainActivity,currentLocation,weatherViewModel)

                }
            }
        }
    }

//    @Composable
//    fun LocationScreen(
//        context: Context,
//        currentLocation: MyLatLng,
//        weatherViewModel: WeatherViewModel
//    ) {
//        var locationRequired by remember { mutableStateOf(false) }
//
//        val launchMultiplePermissions = rememberLauncherForActivityResult(
//            ActivityResultContracts.RequestMultiplePermissions()
//        ) { permissionMap ->
//            val areGranted = permissionMap.values.reduce { accepted, next -> accepted && next }
//
//            if (areGranted) {
//                locationRequired = true
//                // Request location updates
//                weatherViewModel.refreshWeatherData("Your City Name") // Replace with your city
//            }
//        }
//
//        val systemUIController = rememberSystemUiController()
//
//        DisposableEffect(key1 = true) {
//            systemUIController.isSystemBarsVisible = false
//            onDispose {
//                systemUIController.isSystemBarsVisible = true
//            }
//        }
//
//        LaunchedEffect(key1 = currentLocation) {
//            coroutineScope {
//                if (permissions.all {
//                        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
//                    }) {
//                    locationRequired = true
//                    weatherViewModel.refreshWeatherData("Your City Name") // Replace with your city
//                } else {
//                    launchMultiplePermissions.launch(permissions)
//                }
//            }
//        }
//
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(text = "Current Weather")
//            Spacer(modifier = Modifier.height(16.dp))
//
//            if (locationRequired) {
//                // Display weather information here using the data from the ViewModel
//                val weatherData by weatherViewModel.weatherData.observeAsState()
//
//                weatherData?.let { weatherList ->
//                    // Iterate through weatherList and display data
//                    for (weatherItem in weatherList) {
//                        Text(text = "Temperature: ${weatherItem.temperature}")
//                        Text(text = "Description: ${weatherItem.description}")
//                        // Add more details as needed
//                    }
//                }
//            } else {
//                Text(text = "Location permission not granted.")
//            }
//        }
//    }



    private fun initLocationClient() {
        fusedLocationProviderClient = LocationServices
            .getFusedLocationProviderClient(this)
    }
}
