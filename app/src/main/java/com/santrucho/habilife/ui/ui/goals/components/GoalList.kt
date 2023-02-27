package com.santrucho.habilife.ui.ui.goals.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.utils.Resource

@Composable
fun GoalList(
    goalsViewModel: GoalViewModel
) {

    val goals = goalsViewModel.goalState.collectAsState()

    //Makes the logic to collect and show the list of habits created by the user,
    //in case is correct show the list and in case is incorrect show an error

    goals.value.let { result ->
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
                GoalsUI(goals = result.data)
            }
            is Resource.Failure -> {
                LaunchedEffect(goals.value){
                    result.exception.message.toString()
                }
            }
            else -> {IllegalAccessException()}
        }
    }
}

//Makes the LazyColumn or "RecyclerView" to show a list of each Goal created
@Composable
fun GoalsUI(goals:List<GoalsResponse>){
    LazyColumn(modifier = Modifier.padding(8.dp)){
        items(goals) {
            GoalCard(goal = it)
        }
    }
}