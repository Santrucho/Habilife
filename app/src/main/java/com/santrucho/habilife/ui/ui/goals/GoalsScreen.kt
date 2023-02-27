package com.santrucho.habilife.ui.ui.goals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.santrucho.habilife.R
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.ui.goals.components.GoalList

@Composable
fun GoalsScreen(goalViewModel: GoalViewModel, navController: NavController) {

    goalViewModel.resetResult()
    //val localContext = LocalContext.current
    Column(Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally) {
        //Set each element inside column
        Text(text = "MIS OBJETIVOS", modifier = Modifier.padding(8.dp), color = Color.Black)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.83f)
                .background(colorResource(id = R.color.white)),
            horizontalAlignment = Alignment.Start
        ) {
            GoalList(goalViewModel)
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