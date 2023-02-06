package com.santrucho.habilife.ui.ui.habits


import android.graphics.Color
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MagnifierStyle.Companion.Default
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.data.model.HabitResponse
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.ui.goals.components.GoalsUI
import com.santrucho.habilife.ui.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow


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