package com.santrucho.habilife.ui.ui.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santrucho.habilife.R
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.ui.login.LoginBase

@Composable
fun SignUpScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            SignUpBase()
            Spacer(modifier = Modifier.padding(10.dp))

            Button(
                onClick = {
                    navController.navigate(Screen.AppScaffold.route)
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
            ) {
                Text(
                    text = "Create account"
                )
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .wrapContentHeight(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "You already have account? Sign in",
                modifier = Modifier.clickable(onClick = {
                    navController.navigate(Screen.LoginScreen.route)
                }), color = Color.Black, fontSize = 14.sp
            )
        }
    }
}