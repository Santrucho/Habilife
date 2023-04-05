package com.santrucho.habilife.ui.ui.goals.components

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.ui.goals.addgoal.GoalImage
import com.santrucho.habilife.ui.util.LogBundle
import com.santrucho.habilife.ui.util.helper.ProgressBarHelper
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

//Set the visualization and the way in which each Goal will be displayed

@Composable
fun GoalCard(goal: GoalsResponse, navController: NavController) {

    val context = LocalContext.current
    val firebaseAnalytics : FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .clickable {
                navController.navigate(
                    "goal_detail_screen/${
                        Uri.encode(
                            Json
                                .encodeToJsonElement(
                                    goal
                                )
                                .toString()
                        )
                    }"
                )
            }
    ) {
        Card(
            modifier = Modifier
                .wrapContentSize()
                .clickable { LogBundle.logBundleAnalytics(firebaseAnalytics,"Goal Card","goal_card_pressed") }
                .padding(8.dp),
            elevation = 6.dp,
            backgroundColor = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth().padding(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    GoalImage(
                        imageModel = goal.image,
                        modifier = Modifier
                    )
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(0.dp, 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            Row(modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = goal.title,
                                    modifier = Modifier.wrapContentWidth(),
                                    fontSize = 22.sp
                                )
                                Icon(
                                    imageVector = Icons.Outlined.ArrowForward,
                                    modifier = Modifier.width(28.dp),
                                    contentDescription = "arrow forward",
                                    tint = MaterialTheme.colors.primary,
                                )
                            }
                            Divider(modifier = Modifier.padding(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = "Fecha objetivo: ",
                                    modifier = Modifier.wrapContentWidth(),
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = goal.release_date,
                                    modifier = Modifier.wrapContentWidth(),
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                    ) {
                        ProgressBarHelper(goal = goal)
                    }
                }
            }
        }
    }
}
