package com.santrucho.habilife.ui.ui.goals

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.ui.goals.components.FilterGoals
import com.santrucho.habilife.ui.ui.habits.components.MyChip
import com.santrucho.habilife.ui.utils.BackPressHandler
import com.santrucho.habilife.ui.utils.Resource

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GoalsScreen(goalViewModel: GoalViewModel, navController: NavController) {

    goalViewModel.resetResult()
    goalViewModel.getAllGoals()


    val onBack = { navController.navigate(BottomNavScreen.Home.screen_route) }
    BackPressHandler(onBackPressed = onBack)

    val goalState = goalViewModel.goalState.collectAsState()

    val goalCount = goalState.value.let { result ->
        when (result) {
            is Resource.Success -> result.data.size
            else -> 0
        }
    }

    var selectedChip by remember { mutableStateOf("Todos") }

    Column(Modifier.fillMaxSize()) {
        //Set each element inside column
        Spacer(modifier = Modifier.padding(4.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .padding(8.dp)
                .clickable { },
            shape = MaterialTheme.shapes.large,
            elevation = 8.dp,
            backgroundColor = MaterialTheme.colors.secondary
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
            ) {
                Text(
                    text = "OBJETIVOS",
                    color = Color.White,
                    modifier = Modifier.shadow(
                        elevation = 24.dp,
                        ambientColor = Color.Black,
                        spotColor = Color.Black
                    ),
                    fontSize = 32.sp,
                    fontWeight = FontWeight(900),
                )
                Text(
                    text = "$goalCount",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .shadow(
                            elevation = 16.dp,
                            ambientColor = Color.Black,
                        ),
                    color = Color.White,
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MyChip(
                title = "Todos",
                selected = selectedChip == "Todos",
                onSelected = { selected ->
                    selectedChip = if (selected) {
                        "Todos"
                    } else {
                        ""
                    }
                }
            )
            MyChip(
                title = "Año",
                selected = selectedChip == "Año",
                onSelected = { selected ->
                    selectedChip = if (selected) {
                        "Año"
                    } else {
                        ""
                    }
                }
            )
            MyChip(
                title = "Mes",
                selected = selectedChip == "Mes",
                onSelected = { selected ->
                    selectedChip = if (selected) {
                        "Mes"
                    } else {
                        ""
                    }
                }
            )

        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            horizontalAlignment = Alignment.Start
        ) {
            FilterGoals(
                title = selectedChip,
                goalViewModel = goalViewModel,
                navController = navController
            )
        }
        //Set FAB Button Row above BottomBar
        Spacer(modifier = Modifier.weight(1f))
        FabButton(navController)
        Spacer(modifier = Modifier.height(60.dp))
    }
}

//Set Fab Button which navigate to Create New Habit screen
@Composable
fun FabButton(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp), horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { navController.navigate(Screen.NewGoalScreen.route) },
            modifier = Modifier
                .defaultMinSize(256.dp, 56.dp),
            shape = CircleShape

        ) {
            Text("Crear nuevo objetivo")
        }
    }
}
