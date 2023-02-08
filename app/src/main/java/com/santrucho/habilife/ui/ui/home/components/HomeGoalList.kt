package com.santrucho.habilife.ui.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santrucho.habilife.ui.data.model.Goals
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.ui.goals.components.GoalCard
import com.santrucho.habilife.ui.utils.Resource

@Composable
fun HomeGoalList(goalViewModel: GoalViewModel) {

    val goal = goalViewModel.goalState.collectAsState()

    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = 3.dp,
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp, 0.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Proximo objetivo",
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.wrapContentWidth(Alignment.Start),
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp
                )
                TextButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = "Ver todos",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.wrapContentWidth(Alignment.End),
                        textAlign = TextAlign.End,
                        fontSize = 12.sp
                    )
                }
            }
            goal.value.let { result ->
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
                        GoalOfTheDay(
                            goals = result.data,
                            goalViewModel::deleteGoal
                        )
                    }
                    is Resource.Failure -> {
                        LaunchedEffect(goal.value) {
                            result.exception.message.toString()
                        }
                    }
                    else -> {
                        IllegalAccessException()
                    }
                }
            }
        }
    }
}

@Composable
fun GoalOfTheDay(goals: List<Goals>, onDelete: (Goals) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val filteredList = goals.filter { it.release_date.contains("02") }
        if (filteredList.isEmpty()) {
            EmptyGoals()
        } else {
            LazyColumn(modifier = Modifier.padding(0.dp,0.dp,0.dp,8.dp)) {
                items(filteredList) {
                    GoalCard(goal = it, onDelete = onDelete)
                }
            }
        }
    }
}

@Composable
fun EmptyGoals() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No tienes ningun objetivo actualmente!" +
                    "Crea uno nuevo y hazlo!!",
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray,
            modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally),
            textAlign = TextAlign.Start,
            fontSize = 20.sp
        )
    }
}
