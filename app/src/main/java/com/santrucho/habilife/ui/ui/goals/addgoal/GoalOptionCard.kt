package com.santrucho.habilife.ui.ui.goals.addgoal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.santrucho.habilife.ui.data.model.goals.GoalsOption
import com.santrucho.habilife.ui.navigation.Screen

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GoalOptionCard(goalOption: GoalsOption, navController: NavController) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize()
        .clickable {
            navController.navigate("${Screen.AddGoal.route}/${goalOption.type}")
        }) {
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
                        imageModel = goalOption.image,
                        textType = goalOption.type,
                        modifier = Modifier
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
                            Text(
                                text = goalOption.title,
                                modifier = Modifier.fillMaxWidth(),
                                color = Color.Black,
                                fontSize = 22.sp
                            )
                            Divider(modifier = Modifier.padding(4.dp))
                            Text(
                                text = goalOption.information,
                                modifier = Modifier.fillMaxWidth(),
                                color = Color.Black,
                                fontSize = 14.sp
                            )

                            Spacer(modifier = Modifier.weight(1f))

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GoalImage(imageModel: String, textType: String, modifier: Modifier, showText: Boolean = true) {
    Box(modifier = Modifier.wrapContentHeight()) {
        AsyncImage(
            model = imageModel,
            contentDescription = "background image",
            modifier = modifier
                .width(132.dp)
                .height(132.dp)
                .padding(4.dp)
        )
        if (showText) {
            Surface(
                color = Color.White.copy(alpha = 0.6f),
                modifier = Modifier
                    .width(132.dp)
                    .wrapContentHeight()
                    .padding(4.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = textType,
                    modifier = Modifier
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }
        }
    }
}