package com.santrucho.habilife.ui.ui.goals.addgoal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.santrucho.habilife.ui.data.model.goals.GoalsOption
import com.santrucho.habilife.ui.navigation.Screen


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
                    .padding(4.dp)
            ) {
                Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Start) {
                    GoalImage(
                        imageModel = goalOption.image,
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
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = goalOption.title,
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
                            Text(
                                text = goalOption.information,
                                modifier = Modifier.fillMaxWidth(),
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
fun GoalImage(imageModel: String, modifier: Modifier) {
    Box(modifier = Modifier.wrapContentHeight()) {
        AsyncImage(
            model = imageModel,
            contentDescription = "background image",
            modifier = modifier
                .width(106.dp)
                .height(106.dp)
                .padding(4.dp)
                .clip(CircleShape)
        )
    }
}