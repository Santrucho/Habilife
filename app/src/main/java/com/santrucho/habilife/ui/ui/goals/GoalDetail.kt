package com.santrucho.habilife.ui.ui.goals

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.ui.goals.addgoal.GoalImage
import com.santrucho.habilife.ui.ui.habits.DetailsAppBar
import com.santrucho.habilife.ui.utils.BackPressHandler
import com.santrucho.habilife.ui.utils.TypeFieldDetail

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalGlideComposeApi::class)

@Composable
fun GoalDetail(goal: GoalsResponse, goalViewModel: GoalViewModel, navController: NavController) {

    val onBack = { navController.navigate(BottomNavScreen.Goals.screen_route) }
    BackPressHandler(onBackPressed = onBack)
    Scaffold(
        topBar = { DetailsAppBar(onBack, "Mi objetivo") }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                GoalImage(
                    imageModel = goal.image,
                    textType = goal.type,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(4.dp)
                        .clip(RoundedCornerShape(10.dp))
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    GoalField(text = "objetivo", goalText = goal.title, modifier = Modifier.weight(1f))
                    GoalField(text = "fecha objetivo", goalText = goal.release_date,modifier = Modifier.weight(1f))
                }
                GoalField(text = "descripcion", goalText = goal.description,modifier = Modifier.fillMaxWidth())
                TypeFieldDetail(goal, goalViewModel = goalViewModel)

                Row(Modifier.padding(top = 10.dp)) {
                    OutlinedButton(
                        onClick = { navController.navigate(BottomNavScreen.Goals.screen_route) },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "Cancel")
                    }

                    Button(
                        onClick = {
                            goalViewModel.updateGoal(
                                goal,
                                goal.amount,
                                goalViewModel.amountValue.value
                            )
                            navController.navigate(BottomNavScreen.Goals.screen_route)
                        },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "Confirm")

                    }
                }
            }
        }
    }
}

@Composable
fun GoalField(text: String, goalText: String, modifier : Modifier = Modifier) {

    Box(modifier = modifier) {
        Card(elevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = text,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                    color = Color.Blue,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif
                )
                Text(
                    text = goalText,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .align(CenterHorizontally),
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }
        }
    }
}