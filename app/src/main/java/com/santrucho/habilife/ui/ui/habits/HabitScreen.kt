package com.santrucho.habilife.ui.ui.habits

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.ui.habits.components.HabitList
import com.santrucho.habilife.ui.ui.habits.components.HabitUI
import com.santrucho.habilife.ui.utils.Resource

@Composable
fun HabitScreen(
    habitViewModel: HabitViewModel,
    navController: NavController,
) {

    habitViewModel.resetResult()
    //Set the screen in habits
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        MyHabitsSection(habitViewModel = habitViewModel)
        Spacer(modifier = Modifier.weight(1f))
        FABButton(navController = navController)
    }
}

//Set the button to access or navigate to create a new habit
@Composable
fun FABButton(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { navController.navigate(Screen.NewHabitScreen.route) },
            modifier = Modifier.defaultMinSize(240.dp, 56.dp),
            shape = CircleShape

        ) {
            Text("Crear nuevo habito")
        }
    }
    Spacer(modifier = Modifier.height(60.dp))
}

//Set and display the currents habits create for the user
//Calling the list of habits in HabitList
@Composable
fun MyHabitsSection(habitViewModel: HabitViewModel) {

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
                    text = "Mis habitos",
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.wrapContentWidth(Alignment.Start),
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp
                )
            }
            HabitList(habitViewModel)
        }
    }
}




