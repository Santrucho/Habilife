package com.santrucho.habilife.ui.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
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
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.ui.habits.HabitCard
import com.santrucho.habilife.ui.utils.Resource

@Composable
fun HomeHabitList(habitViewModel:HabitViewModel){

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Habitos del dia",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }

    val habit = habitViewModel.habitState.collectAsState()

    habit.value.let { result ->
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
                HabitOfTheDay(
                    habits = result.data,
                    habitViewModel::deleteHabit
                )
            }
            is Resource.Failure -> {
                LaunchedEffect(habit.value){
                    result.exception.message.toString()
                }
            }
            else -> {IllegalAccessException()}
        }
    }
}


@Composable
fun HabitOfTheDay(habits:List<Habit>, onDelete:(Habit)-> Unit){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column( modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally){
            LazyColumn(modifier = Modifier.padding(8.dp)){
                items(habits.filter { it.frequently.contains("abababab") }) {
                    HabitCard(habit = it, onDelete = onDelete)
                }
            }
        }
    }
}