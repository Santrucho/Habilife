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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow


@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HabitList(
    habitsStateFlow: StateFlow<List<Habit>>,
    isRefreshing: Boolean,
    refreshData: () -> Unit,
    habitViewModel: HabitViewModel
) {

    val habits by habitsStateFlow.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize(),
    ){
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = refreshData
        ){
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = habits
                ){ habit ->
                    Log.d("[[[[[[[[[[[[[[[[[[[[[[[[[[",habit.toString())
                    Log.d("[[[[[[[[[[[[[[[[[[[[[[[[[[",habit.toString())

                    var isDeleted by remember { mutableStateOf(false) }
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            Log.d("HabitList", "Dismiss value: ${it.name}")
                            if(it == DismissValue.DismissedToEnd) isDeleted = !isDeleted
                            it != DismissValue.DismissedToEnd
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(DismissDirection.StartToEnd),
                        dismissThresholds = {
                            FractionalThreshold(0.5f)
                        },
                        background = {
                            val direction = dismissState.dismissDirection ?:  return@SwipeToDismiss
                            val color by animateColorAsState(
                                when(dismissState.targetValue) {
                                    DismissValue.DismissedToEnd -> androidx.compose.ui.graphics.Color.Companion.Red
                                    DismissValue.DismissedToStart -> androidx.compose.ui.graphics.Color.Companion.Red
                                    DismissValue.Default -> androidx.compose.ui.graphics.Color.Companion.Red
                                }
                            )
                            val alignment = when (direction) {
                                DismissDirection.StartToEnd -> Alignment.CenterStart
                                DismissDirection.EndToStart -> Alignment.CenterEnd

                            }
                            val scale by animateFloatAsState(
                                if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                            )

                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(horizontal = 20.dp),
                                contentAlignment = alignment
                            ) {
                            }
                        }
                    ) {
                        if(isDeleted) {
                            habitViewModel.deleteHabit(habit)
                        } else {
                            HabitCard(habit = habit)
                        }
                    }
                }
            }
        }


        if (habits.isEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    text = "Error al mostrar los habitos",
                    textAlign = TextAlign.Center
                )
            }
        }
    }