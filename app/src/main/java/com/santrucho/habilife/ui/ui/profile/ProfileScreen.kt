package com.santrucho.habilife.ui.ui.profile


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.ktx.Firebase
import com.santrucho.habilife.R
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.presentation.LoginViewModel
import com.santrucho.habilife.ui.presentation.SignUpViewModel
import com.santrucho.habilife.ui.util.Resource

@Composable
fun ProfileScreen(navController: NavController,goalViewModel: GoalViewModel,habitViewModel: HabitViewModel,loginViewModel: LoginViewModel, signUpViewModel: SignUpViewModel,) {

    val habitComplete = habitViewModel.habitComplete.value ?: 0

    LaunchedEffect(Unit) {
        habitViewModel.getHabitComplete()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            signUpViewModel.currentUser?.let {
                UserInfo(
                    name = it.displayName.toString(),
                    email = it.email.toString(),
                    habitCount = habitComplete.toString()
                )
            }
            Button(
                onClick = {
                    loginViewModel.logout()
                    signUpViewModel.logout()
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(BottomNavScreen.Profile.screen_route) { inclusive = true }
                    }
                    goalViewModel.resetValue()
                    habitViewModel.resetValue()
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
            ) {
                Text(
                    text = "Cerrar sesion"
                )
            }
        }
    }
}
