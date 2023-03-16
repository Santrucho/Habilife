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
import com.santrucho.habilife.ui.util.Resource

@Composable
fun HomeGoalList(navController: NavController,goalViewModel: GoalViewModel) {

    val goal = goalViewModel.goalState.collectAsState()
    //Set the box to show the Goals to make that day
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = 3.dp,
        backgroundColor = MaterialTheme.colors.secondaryVariant,
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
                    modifier = Modifier.wrapContentWidth(Alignment.Start),
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp
                )
                TextButton(onClick = {navController.navigate(BottomNavScreen.Goals.screen_route) }) {
                    Text(
                        text = "Ver todos",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.wrapContentWidth(Alignment.End),
                        textAlign = TextAlign.End,
                        fontSize = 12.sp
                    )
                }
            }
            //Make the logic to call a list of Goals which coincide with the current day
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
                            EmptyMessage("objetivo")
                        }
                        else{
                            GoalsUI(
                                filteredList,navController
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

//In case there doesn't exist any Habit or Goals to make that day, show a Empty Message to notify the user
@Composable
fun EmptyMessage(text:String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No tienes ningun $text para realizar hoy!!\n" +
                    "Crea uno nuevo!!",
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray,
            modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally),
            textAlign = TextAlign.Start,
            fontSize = 16.sp
        )
    }
}
