package com.santrucho.habilife.ui.ui.profile


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.presentation.LoginViewModel
import com.santrucho.habilife.ui.util.LogBundle
import com.santrucho.habilife.ui.util.Resource

@Composable
fun ProfileScreen(
    navController: NavController,
    goalViewModel: GoalViewModel,
    habitViewModel: HabitViewModel,
    loginViewModel: LoginViewModel,
) {

    val context = LocalContext.current
    val firebaseAnalytics : FirebaseAnalytics = FirebaseAnalytics.getInstance(context)
    LogBundle.logBundleAnalytics(firebaseAnalytics,"Profile Screen View","profile_screen_view")

    val habitComplete = habitViewModel.habitComplete.value ?: 0
    val habit = habitViewModel.habitState.collectAsState()

    val goalComplete = goalViewModel.goalComplete.value ?: 0
    Log.d("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@",goalViewModel.goalComplete.value.toString())
    val goal = goalViewModel.goalState.collectAsState()

    val actualHabits = getActualStats(flow = habit)
    val actualGoals = getActualStats(flow = goal)

    LaunchedEffect(Unit) {
        habitViewModel.getHabitComplete()
        goalViewModel.getGoalComplete()
    }
    Column(
        modifier = Modifier
            .fillMaxSize().background(MaterialTheme.colors.secondaryVariant),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        loginViewModel.currentUser?.let {
            UserInfo(
                name = it.displayName.toString(),
                email = it.email.toString(),
                loginViewModel::logout,
                navController = navController
            )
        }
        StatsSection(actualHabits.toString(),habitComplete.toString(),actualGoals.toString(),goalComplete.toString())
    }
}


fun getActualStats(flow: State<Resource<List<Any>>?>) : Int{
    val flowValue = flow.value.let { resource ->
        when (resource) {
            is Resource.Success -> {
                resource.data.size
            }
            else -> {
                0
            }
        }
    }
    return flowValue
}
