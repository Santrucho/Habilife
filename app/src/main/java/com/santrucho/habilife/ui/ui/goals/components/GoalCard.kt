package com.santrucho.habilife.ui.ui.goals.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse

//Set the visualization and the way in which each Goal will be displayed
@OptIn(ExperimentalMaterialApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun GoalCard(goal: GoalsResponse) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .clickable {}
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
                Box(modifier = Modifier.wrapContentHeight()) {
                    GlideImage(
                        model = goal.image,
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
                            text = goal.title,
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
                            text = goal.description,
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
                                text = goal.release_date,
                                modifier = Modifier
                                    .padding(0.dp, 8.dp)
                                    .wrapContentWidth(Alignment.Start),
                                color = Color.Black,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
