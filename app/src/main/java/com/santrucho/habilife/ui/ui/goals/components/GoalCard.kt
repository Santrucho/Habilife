package com.santrucho.habilife.ui.ui.goals.components

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.ui.goals.GoalDetail
import com.santrucho.habilife.ui.ui.goals.addgoal.GoalImage
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

//Set the visualization and the way in which each Goal will be displayed
@OptIn(ExperimentalMaterialApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun GoalCard(goal: GoalsResponse,navController:NavController) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .clickable {
                navController.navigate("goal_detail_screen/${Uri.encode(Json.encodeToJsonElement(goal).toString())}")
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
                    .padding(all = 12.dp)
            ) {
                GoalImage(imageModel = goal.image, textType = goal.type,modifier = Modifier.fillMaxWidth())
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(0.dp, 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        Text(
                            text = goal.title,
                            modifier = Modifier
                                .padding(8.dp),
                            color = Color.Black,
                            fontSize = 16.sp
                        )
                        Text(
                            text = goal.description,
                            modifier = Modifier
                                .padding(8.dp),
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
