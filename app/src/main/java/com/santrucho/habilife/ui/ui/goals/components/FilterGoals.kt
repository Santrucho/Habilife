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
import androidx.navigation.NavController
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.util.Resource
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun FilterGoals(title: String, goalViewModel: GoalViewModel,navController:NavController) {

    val goalState = goalViewModel.goalState.collectAsState()

    goalState.value.let { result ->
        when (result) {
            is Resource.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
            is Resource.Success -> {
                val formatter = DateTimeFormatter.ofPattern("dd MM yyyy")
                val filterByMonth = result.data.filter { goal ->
                    val releaseDate = LocalDate.parse(goal.release_date,formatter)
                    releaseDate.year == YearMonth.now().year && releaseDate.month == YearMonth.now().month
                }
                val filterByYear = result.data.filter { goal ->
                    val releaseDate = LocalDate.parse(goal.release_date,formatter)
                    releaseDate.year == YearMonth.now().year
                }
                when (title) {
                    "Mes" -> {
                        GoalsUI(goals = filterByMonth, navController = navController)
                    }
                    "Año" -> {
                        GoalsUI(goals = filterByYear, navController = navController)
                    }
                    else -> GoalsUI(goals = result.data, navController = navController)
                }

            }
            is Resource.Failure -> {
                LaunchedEffect(goalState.value) {
                    result.exception.message.toString()
                }
            }
            else -> {
                IllegalAccessException()
            }
        }
    }
}

//Makes the LazyColumn or "RecyclerView" to show a list of each Goal created
@Composable
fun GoalsUI(goals:List<GoalsResponse>,navController:NavController){
    LazyColumn(modifier = Modifier.padding(4.dp)){
        items(goals) {
            GoalCard(goal = it, navController = navController)
        }
    }
}