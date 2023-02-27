package com.santrucho.habilife.ui.ui.habits.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.utils.Resource
import kotlin.reflect.KFunction2

@Composable
fun HabitList(habitViewModel: HabitViewModel){
    val habits = habitViewModel.habitState.collectAsState()

    //Makes the logic to collect and show the list of habits created by the user,
    //in case is correct show the list and in case is incorrect show an error
    habits.value.let { result ->
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
                HabitUI(habits = result.data, habitViewModel::deleteHabit,habitViewModel)
            }
            is Resource.Failure -> {
                LaunchedEffect(habits) {
                    result.exception.message.toString()

                }
            }
            else -> {
                IllegalAccessException()
            }
        }
    }
}
//Makes the LazyColumn or "RecyclerView" to show a list of each Habit created
@Composable
fun HabitUI(
    habits: List<Habit>, onDelete: (Habit) -> Unit,viewModel: HabitViewModel
) {
    LazyColumn(modifier = Modifier.padding(8.dp)) {
        items(habits) {
            HabitCard(habit = it, onDelete,viewModel)
        }
    }
}