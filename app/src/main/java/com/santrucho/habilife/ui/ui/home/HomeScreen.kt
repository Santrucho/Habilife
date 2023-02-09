package com.santrucho.habilife.ui.ui.home

import androidx.compose.foundation.background
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santrucho.habilife.R
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.presentation.*
import com.santrucho.habilife.ui.ui.habits.HabitCard
import com.santrucho.habilife.ui.ui.home.components.HomeGoalList
import com.santrucho.habilife.ui.ui.home.components.HomeHabitList
import com.santrucho.habilife.ui.utils.Resource


@Composable
fun HomeScreen(navController: NavController,userViewModel: SignUpViewModel,goalViewModel: GoalViewModel,habitViewModel: HabitViewModel) {

    goalViewModel.getAllGoals()
    habitViewModel.getAllHabits()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        userViewModel.currentUser?.let{
            UserInfo(name = it.displayName.toString())
        }
        HomeHabitList(navController,habitViewModel)
        Spacer(modifier = Modifier.height(18.dp))
        HomeGoalList(navController,goalViewModel)
    }
}

@Composable
fun UserInfo(name:String){
    Spacer(modifier = Modifier.height(32.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenido, $name",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}