package com.santrucho.habilife.ui.ui.goals

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.ui.goals.addgoal.GoalImage
import com.santrucho.habilife.ui.ui.habits.DetailsAppBar
import com.santrucho.habilife.ui.util.BackPressHandler
import com.santrucho.habilife.ui.util.TypeFieldDetail


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GoalDetail(goal: GoalsResponse, goalViewModel: GoalViewModel, navController: NavController) {

    val onBack = { navController.navigate(BottomNavScreen.Goals.screen_route) }
    BackPressHandler(onBackPressed = onBack)

    Scaffold(
        topBar = { DetailsAppBar(onBack) },
        backgroundColor = MaterialTheme.colors.secondaryVariant
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(1f)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth().fillMaxHeight(0.90f)
                    .padding(8.dp)
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(8.dp), elevation = 6.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                GoalImage(
                                    imageModel = goal.image,
                                    modifier = Modifier
                                        .width(160.dp)
                                        .height(160.dp),
                                )
                                Box(
                                    modifier = Modifier
                                        .wrapContentSize()
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                            .padding(horizontal = 8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Top
                                    ) {
                                        GoalField(
                                            text = "Objetivo",
                                            goalText = goal.title,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Divider(modifier = Modifier.padding(4.dp))
                                        GoalField(
                                            text = "Fecha objetivo",
                                            goalText = goal.release_date,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }
                            }
                            Divider(modifier = Modifier.padding(4.dp))
                            GoalField(
                                text = "Descripcion",
                                goalText = goal.description,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(8.dp), elevation = 6.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            TypeFieldDetail(goal, goalViewModel = goalViewModel)
                        }
                    }
                }
            }

            Row(modifier = Modifier.padding(8.dp).fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedButton(
                    onClick = { navController.navigate(BottomNavScreen.Goals.screen_route) },
                    Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .height(48.dp)
                ) {
                    Text(text = "Cancel")
                }
                Button(
                    onClick = {
                        goalViewModel.confirmSubject.value = true
                        goalViewModel.updateGoal(
                            goal,
                            goal.amount,
                            goalViewModel.amountValue.value,
                            goal.kilometers,
                            goalViewModel.trainingValue.value,
                            goal.subjectApproved
                        )
                        navController.navigate(BottomNavScreen.Goals.screen_route)
                    },
                    Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .height(48.dp)
                ) {
                    Text(text = "Confirm")
                }
            }
            Spacer(modifier = Modifier.padding(56.dp))
        }
    }
}


@Composable
fun GoalField(text: String, goalText: String, modifier: Modifier = Modifier) {

    Box(modifier = modifier) {

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                color = MaterialTheme.colors.secondary,
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif
            )
            Text(
                text = goalText,
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}