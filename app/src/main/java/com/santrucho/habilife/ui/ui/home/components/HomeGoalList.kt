package com.santrucho.habilife.ui.ui.home.components

import androidx.compose.foundation.layout.*
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
import androidx.navigation.NavController
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.ui.goals.components.GoalsUI
import com.santrucho.habilife.ui.utils.Resource

@Composable
fun HomeGoalList(navController: NavController,goalViewModel: GoalViewModel) {

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
                .wrapContentSize(),
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
                TextButton(onClick = {navController.navigate(BottomNavScreen.Goals.screen_route) }) {
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
                        val filteredList = result.data.filter{it.release_date.contains("1")}
                        if (filteredList.isEmpty()) {
                            EmptyMessage("No tienes ningun habito actualmente!\nCrea uno nuevo!!")
                        }
                        else{
                            GoalsUI(
                                filteredList,
                                goalViewModel::deleteGoal
                            )
                        }
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
fun EmptyMessage(text:String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray,
            modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally),
            textAlign = TextAlign.Start,
            fontSize = 16.sp
        )
    }
}
