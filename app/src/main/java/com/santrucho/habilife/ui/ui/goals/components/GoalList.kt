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
import com.santrucho.habilife.ui.data.model.GoalsResponse

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun GoalList(
    state: GoalsResponse,
    isRefreshing: Boolean,
    refreshData: () -> Unit
) {

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
                    items = state.goalsList
                ){ goal ->

                    var isDeleted by remember { mutableStateOf(false) }
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            Log.d("BookList", "Dismiss value: ${it.name}")
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
                                    DismissValue.DismissedToEnd -> Color.Red
                                    DismissValue.DismissedToStart -> Color.Red
                                    DismissValue.Default -> Color.Red
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
                            // TODO("DELETE BOOK")
                        } else {
                            GoalCard(goal)
                        }
                    }
                }
            }
        }


        if (state.error.isNotBlank()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = state.error,
                textAlign = TextAlign.Center
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp))
        }
    }
}