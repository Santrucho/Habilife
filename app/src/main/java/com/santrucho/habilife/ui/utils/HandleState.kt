package com.santrucho.habilife.ui.utils

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HandleState(flow: State<Resource<Any>?>, navController: NavController,route:String,text:String) {
    val context = LocalContext.current
    flow.value?.let {
        when (it) {
            is Resource.Success -> {
                LaunchedEffect(Unit) {
                    navController.navigate(route) {
                        popUpTo(route) { inclusive = true }
                    }
                    Toast.makeText(
                        context,
                        "$text correctamente!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            is Resource.Failure -> {
                LaunchedEffect(flow.value) {
                    Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG)
                        .show()
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