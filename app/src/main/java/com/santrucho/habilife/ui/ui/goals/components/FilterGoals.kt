package com.santrucho.habilife.ui.ui.goals.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.ui.home.components.EmptyMessage
import com.santrucho.habilife.ui.util.Resource
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun FilterGoals(title: String, goalViewModel: GoalViewModel, navController: NavController) {

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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.75f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    when (title) {
                        "Mes" -> {
                            val formatter = DateTimeFormatter.ofPattern("dd MM yyyy")
                            val filterByMonth = result.data.filter { goal ->
                                val releaseDate = LocalDate.parse(goal.release_date, formatter)
                                releaseDate.year == YearMonth.now().year && releaseDate.month == YearMonth.now().month
                            }
                            Box(
                                contentAlignment = if (filterByMonth.isEmpty()) Alignment.Center else Alignment.TopCenter,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                if (filterByMonth.isEmpty()) {
                                    EmptyMessage(
                                        text = "No tienes objetivos para realizar este mes",
                                        textClick = "Crea nuevos objetivos!"
                                    )
                                } else {
                                    GoalsUI(goals = filterByMonth, navController = navController)
                                }
                            }
                        }

                        "Año" -> {
                            val formatter = DateTimeFormatter.ofPattern("dd MM yyyy")
                            val filterByYear = result.data.filter { goal ->
                                val releaseDate = LocalDate.parse(goal.release_date, formatter)
                                releaseDate.year == YearMonth.now().year
                            }
                            Box(
                                contentAlignment = if (filterByYear.isEmpty()) Alignment.Center else Alignment.TopCenter,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                if (filterByYear.isEmpty()) {
                                    EmptyMessage(
                                        text = "No tienes objetivos para realizar este año",
                                        textClick = "Crea nuevos objetivos!"
                                    )
                                } else {
                                    GoalsUI(goals = filterByYear, navController = navController)
                                }
                            }
                        }

                        else -> {
                            Box(
                                contentAlignment = if (result.data.isEmpty()) Alignment.Center else Alignment.TopCenter,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                if (result.data.isEmpty()) {
                                    EmptyMessage(
                                        text = "No tienes objetivos para realizar este año",
                                        textClick = "Crea nuevos objetivos!"
                                    )
                                } else {
                                    GoalsUI(goals = result.data, navController = navController)
                                }
                            }
                        }
                    }
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
fun GoalsUI(goals: List<GoalsResponse>, navController: NavController) {
    LazyColumn(modifier = Modifier.padding(4.dp)) {
        items(goals) {
            GoalCard(goal = it, navController = navController)
        }
    }
}