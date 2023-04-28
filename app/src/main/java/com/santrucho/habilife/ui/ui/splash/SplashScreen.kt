package com.santrucho.habilife.ui.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.santrucho.habilife.R
import com.santrucho.habilife.ui.navigation.Screen
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController){
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.primary)){
        LaunchedEffect(key1 = true){
            delay(3000)
            navController.popBackStack()
            navController.navigate(Screen.LoginScreen.route)
        }
        Splash()
    }
}

@Composable
@Preview("Preview")
fun Splash(){
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 32.dp,vertical = 8.dp),
    contentAlignment = Alignment.Center){
            Image(painterResource(id = R.drawable.logo),alignment = Alignment.Center, contentDescription = "Splash logo app",modifier = Modifier.size(140.dp))
    }
}