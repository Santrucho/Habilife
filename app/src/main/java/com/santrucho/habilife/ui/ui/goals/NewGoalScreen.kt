package com.santrucho.habilife.ui.ui.goals.addgoal

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santrucho.habilife.ui.data.model.goals.GoalsOption
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.ui.habits.DetailsAppBar
import com.santrucho.habilife.ui.util.BackPressHandler
import com.santrucho.habilife.ui.util.Resource

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NewGoalScreen(goalViewModel: GoalViewModel,navController:NavController){

    //Makes the logic to collect and show the list of habits created by the user,
    //in case is correct show the list and in case is incorrect show an error
    goalViewModel.getOptionsGoals()
    goalViewModel.resetResult()

    val onBack = { navController.navigate(BottomNavScreen.Goals.screen_route) }
    BackPressHandler(onBackPressed = onBack)

    Scaffold(
        topBar = { DetailsAppBar(onBack) },
        backgroundColor = MaterialTheme.colors.secondaryVariant
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(Modifier.fillMaxSize()) {
                Text(
                    text = "Seleccione un objetivo",
                    modifier = Modifier.padding(8.dp),
                    fontSize = 22.sp
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.Start
                ) {
                    GoalsOptionList(goalViewModel,navController)
                }
            }
        }
    }
}

@Composable
fun GoalsOptionList(goalViewModel: GoalViewModel,navController: NavController){
    val goalsOptionState = goalViewModel.goalsOptionState.collectAsState()

    goalsOptionState.value.let { result ->
        when(result){
            is Resource.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
            is Resource.Success -> {
                GoalsOptionUI(goalsOptions = result.data,navController)
            }
            is Resource.Failure -> {
                LaunchedEffect(goalsOptionState.value){
                    result.exception.message.toString()
                }
            }
            else -> {IllegalAccessException()}
        }
    }
}

@Composable
fun GoalsOptionUI(goalsOptions:List<GoalsOption>, navController: NavController){
    LazyColumn(modifier = Modifier.padding(8.dp)){
        items(goalsOptions) {
            GoalOptionCard(goalOption = it,navController)
        }
    }
}