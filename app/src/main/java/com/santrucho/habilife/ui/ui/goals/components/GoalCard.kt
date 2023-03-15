package com.santrucho.habilife.ui.ui.goals.components

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.ui.goals.addgoal.GoalImage
import com.santrucho.habilife.ui.utils.helper.ProgressBarHelper
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

//Set the visualization and the way in which each Goal will be displayed

@Composable
fun GoalCard(goal: GoalsResponse, navController: NavController) {


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
                .padding(8.dp),
            elevation = 6.dp,
            backgroundColor = MaterialTheme.colors.secondary
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
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
                            Text(
                                text = goal.title,
                                modifier = Modifier.fillMaxWidth(),
                                color = Color.White,
                                fontSize = 22.sp
                            )
                            Divider(modifier = Modifier.padding(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Text(
                                    text = "Fecha objetivo: ",
                                    modifier = Modifier.wrapContentWidth(),
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = goal.release_date,
                                    modifier = Modifier.wrapContentWidth(),
                                    color = Color.White,
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
