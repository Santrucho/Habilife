package com.santrucho.habilife.ui.ui.habits


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
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.utils.Resource


@Composable
fun HabitList(habitViewModel: HabitViewModel) {

    val habits = habitViewModel.habitState.collectAsState()

    habits.value.let { result ->
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
                HabitUI(habits = result.data,habitViewModel::deleteHabit)
            }
            is Resource.Failure -> {
                LaunchedEffect(habits.value){
                    result.exception.message.toString()
                }
            }
            else -> {IllegalAccessException()}
        }
    }
}

@Composable
fun HabitUI(habits:List<Habit>,onDelete : (Habit) -> Unit){
    LazyColumn(modifier = Modifier.padding(8.dp)){
        items(habits) {
            HabitCard(habit = it,onDelete)
        }
    }
}