package com.santrucho.habilife.ui.ui.goals

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.santrucho.habilife.R
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.ui.goals.addgoal.GoalImage
import com.santrucho.habilife.ui.ui.habits.DetailsAppBar
import com.santrucho.habilife.ui.util.BackPressHandler
import com.santrucho.habilife.ui.util.LogBundle
import com.santrucho.habilife.ui.util.TypeFieldDetail


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GoalDetail(goal: GoalsResponse, goalViewModel: GoalViewModel, navController: NavController) {

    val context = LocalContext.current
    val firebaseAnalytics : FirebaseAnalytics = FirebaseAnalytics.getInstance(context)
    LogBundle.logBundleAnalytics(firebaseAnalytics,"Goal Detail View","goal_detail_view")

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
                    .fillMaxWidth()
                    .fillMaxHeight(0.90f)
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
                            Divider(modifier = Modifier.padding(4.dp))
                            DeleteGoal(onDelete = goalViewModel::deleteGoal, navController = navController,goal)
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

            Row(modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedButton(
                    onClick = {
                        navController.navigate(BottomNavScreen.Goals.screen_route)
                        LogBundle.logBundleAnalytics(firebaseAnalytics,"Cancel Goal Update","cancel_goal_update_pressed")},
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
                        LogBundle.logBundleAnalytics(firebaseAnalytics,"Confirm Goal Update","confirm_goal_update_pressed")
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

@Composable
fun DeleteGoal(
    onDelete: (GoalsResponse) -> Unit,
    navController: NavController,
    goal:GoalsResponse
) {
    val context = LocalContext.current
    val firebaseAnalytics : FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    val openDialog = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .wrapContentWidth(Alignment.End),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = {
            openDialog.value = true
            LogBundle.logBundleAnalytics(firebaseAnalytics,"Delete Goal Option","delete_goal_option_pressed")
        }, modifier = Modifier.weight(1f).wrapContentWidth(Alignment.End)) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "delete",
                tint = MaterialTheme.colors.primary
            )
        }
    }
    if (openDialog.value) {
        AlertDialog(onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Eliminar objetivo", fontSize = 22.sp) },
            text = { Text(text = "Estas seguro que desea eliminar el objetivo?", fontSize = 18.sp) },
            confirmButton = {
                TextButton(onClick = {
                    onDelete(goal)
                    navController.navigate(BottomNavScreen.Goals.screen_route)
                    LogBundle.logBundleAnalytics(firebaseAnalytics,"Delete Goal ","delete_goal_pressed")
                }) {
                    Text("Confirmar", color = Color.Black, fontSize = 18.sp)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    openDialog.value = false
                    LogBundle.logBundleAnalytics(firebaseAnalytics,"Delete Goal Cancel","delete_goal_cancel_pressed")
                }) {
                    Text("Cancelar", color = Color.Black, fontSize = 18.sp)
                }
            })
    }
}