package com.santrucho.habilife.ui.ui.profile


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.santrucho.habilife.R
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.presentation.LoginViewModel
import com.santrucho.habilife.ui.presentation.SignUpViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.util.Resource

@Composable
fun ProfileScreen(
    navController: NavController,
    goalViewModel: GoalViewModel,
    habitViewModel: HabitViewModel,
    loginViewModel: LoginViewModel,
    signUpViewModel: SignUpViewModel,
) {

    val habitComplete = habitViewModel.habitComplete.value ?: 0
    val habit = habitViewModel.habitState.collectAsState()

    val actualHabits = habit.value.let { resource ->
        when (resource) {
            is Resource.Success -> {
                resource.data.size
            }
            else -> {
                0
            }
        }
    }

    LaunchedEffect(Unit) {
        habitViewModel.getHabitComplete()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        loginViewModel.currentUser?.let {
            UserInfo(
                name = it.displayName.toString(),
                email = it.email.toString(),
                loginViewModel::logout,
                navController = navController
            )
            Log.d("@@@@@@@@@@@@@@@@@@@@@@@@@@@",loginViewModel.loginFlow.value.toString())
        }
        StatsSection(actualHabits.toString(),habitComplete.toString(),actualHabits.toString(),habitComplete.toString())
    }
}
