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
import com.santrucho.habilife.ui.utils.CustomLinearProgress
import com.santrucho.habilife.ui.utils.ProgressBarHelper
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

//Set the visualization and the way in which each Goal will be displayed
@OptIn(ExperimentalMaterialApi::class, ExperimentalGlideComposeApi::class)
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
                .padding(8.dp), elevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding()
            ) {
                Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Start) {
                    GoalImage(
                        imageModel = goal.image,
                        textType = goal.type,
                        modifier = Modifier.width(144.dp).height(144.dp)
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
                                color = Color.Black,
                                fontSize = 22.sp
                            )
                            Text(
                                text = goal.release_date,
                                modifier = Modifier.fillMaxWidth(),
                                color = Color.Black,
                                fontSize = 14.sp
                            )

                            Spacer(modifier = Modifier.padding(8.dp))
                            ProgressBarHelper(goal = goal)
                        }
                    }
                }
            }
        }
    }
}
