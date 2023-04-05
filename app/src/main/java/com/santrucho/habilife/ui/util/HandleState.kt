package com.santrucho.habilife.ui.util

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.firebase.analytics.FirebaseAnalytics

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HandleState(flow: State<Resource<Any>?>, navController: NavController,route:String,text:String,
                message:String,eventName:String) {

    val context = LocalContext.current
    val firebaseAnalytics : FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    flow.value?.let {
        when (it) {
            is Resource.Success -> {
                LaunchedEffect(Unit) {
                    navController.navigate(route)
                    Toast.makeText(
                        context,
                        "$text correctamente!",
                        Toast.LENGTH_SHORT
                    ).show()
                    LogBundle.logBundleAnalytics(firebaseAnalytics,"$message Succeeded","${eventName}_success")
                }
            }
            is Resource.Failure -> {
                LaunchedEffect(flow.value) {
                    Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG)
                        .show()
                    LogBundle.logBundleAnalytics(firebaseAnalytics,"$message Failure","${eventName}_failure")
                }
            }
            is Resource.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}