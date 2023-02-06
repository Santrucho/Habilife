package com.santrucho.habilife.ui.ui.goals.components

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.santrucho.habilife.ui.data.model.Goals
import com.santrucho.habilife.ui.data.model.GoalsResponse
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.utils.Resource

@Composable
fun GoalList(
    goalsViewModel: GoalViewModel
) {
    val goals = goalsViewModel.goalState.collectAsState()

    goals.value.let { result ->
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
                GoalsUI(goals = result.data,goalsViewModel::deleteGoal)
            }
            is Resource.Failure -> {
                LaunchedEffect(goals.value){
                    result.exception.message.toString()
                }
            }
            else -> {IllegalAccessException()}
        }
    }
}

@Composable
fun GoalsUI(goals:List<Goals>,onDelete:(Goals) -> Unit){
    LazyColumn(modifier = Modifier.padding(8.dp)){
        items(goals) {
            GoalCard(goal = it,onDelete)
        }
    }
}