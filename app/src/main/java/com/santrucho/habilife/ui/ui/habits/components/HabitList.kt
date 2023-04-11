package com.santrucho.habilife.ui.ui.habits.components


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.ui.home.components.EmptyMessage
import com.santrucho.habilife.ui.util.Resource

@Composable
fun HabitList(habitViewModel: HabitViewModel) {
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.65f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        contentAlignment = if (result.data.isEmpty()) Alignment.Center else Alignment.TopCenter,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (result.data.isEmpty()) {
                            EmptyMessage(
                                text = "Al parecer no tienes ningun habito creado ahora mismo",
                                "Crea uno nuevo!"
                            )
                        } else {
                            HabitUI(
                                habits = result.data,
                                habitViewModel::deleteHabit,
                                habitViewModel
                            )
                        }
                    }
                }
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
    habits: List<Habit>, onDelete: (Habit) -> Unit, viewModel: HabitViewModel
) {
    LazyColumn(modifier = Modifier.padding(8.dp)) {
        items(habits) {
            HabitCard(habit = it, onDelete, viewModel)
        }
    }
}