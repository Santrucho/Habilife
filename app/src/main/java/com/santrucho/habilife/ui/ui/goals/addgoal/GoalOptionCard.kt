package com.santrucho.habilife.ui.ui.goals.addgoal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.santrucho.habilife.ui.data.model.goals.GoalsOption
import com.santrucho.habilife.ui.utils.CheckOptions

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GoalOptionCard(goalOption: GoalsOption, navController: NavController) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize()
        .clickable {
            navController.navigate("goal_detail_screen/${goalOption.type}")
        }) {
        Card(
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp), elevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Box(modifier = Modifier.wrapContentHeight()) {
                    GlideImage(
                        model = goalOption.image,
                        contentDescription = "background image",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Surface(
                        color = Color.White.copy(alpha = 0.6f),
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.BottomCenter)
                    ) {
                        Text(
                            text = goalOption.title,
                            modifier = Modifier
                                .padding(8.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally),
                            color = Color.Black,
                            fontSize = 18.sp
                        )
                    }
                }
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
                            text = goalOption.information,
                            modifier = Modifier
                                .padding(8.dp),
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                        Row(
                            modifier = Modifier
                                .wrapContentWidth()
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Duracion:",
                                modifier = Modifier
                                    .padding(8.dp)
                                    .wrapContentWidth(Alignment.Start),
                                color = Color.Black,
                                fontSize = 12.sp
                            )
                            Text(
                                text = goalOption.duration,
                                modifier = Modifier
                                    .padding(0.dp, 8.dp)
                                    .wrapContentWidth(Alignment.Start),
                                color = Color.Black,
                                fontSize = 18.sp
                            )
                            CheckOptions(goalsOption = goalOption)
                        }
                    }
                }
            }
        }
    }
}