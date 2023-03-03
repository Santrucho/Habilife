package com.santrucho.habilife.ui.ui.goals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.request.RequestOptions
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.utils.CustomFieldDialog

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GoalDialog(onDismiss: () -> Unit,onExit:() -> Unit,goal:GoalsResponse,goalViewModel: GoalViewModel){
    /*val newAmount = goal.amount?.let {
        goalViewModel.amountValue.value?.let { amountValue ->
            it + amountValue
        } ?: it
    } ?: 0*/
    Dialog(onDismissRequest = {onDismiss()},
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)){
        Card(shape = RoundedCornerShape(8.dp), modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(), elevation = 8.dp){
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(8.dp)
            ) {
                Box(modifier = Modifier.wrapContentHeight()) {
                        GlideImage(
                            model = goal.image,
                            contentDescription = "background image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(4.dp)
                                .clip(RoundedCornerShape(10.dp)),
                        )
                        Surface(
                            color = Color.White.copy(alpha = 0.6f),
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                        ) {
                            Text(
                                text = goal.type,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .wrapContentWidth(Alignment.CenterHorizontally),
                                color = Color.Black,
                                fontSize = 18.sp
                            )
                        }
                }

                GoalField(text = "Objetivo: ", goalText = goal.title)
                GoalField(text = "Descripcion: ", goalText = goal.description)
                GoalField(text = "Fecha objetivo: ", goalText = goal.release_date )

                CustomFieldDialog(goal, goalViewModel = goalViewModel)

                Row(Modifier.padding(top = 10.dp)) {
                    OutlinedButton(
                        onClick = { onDismiss() },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "Cancel")
                    }

                    Button(
                        onClick = { onExit()
                                  goalViewModel.updateGoal(goal,goal.amount,goalViewModel.amountValue.value)},
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
fun GoalField(text:String,goalText:String){
    Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.padding(8.dp)){
        Text(
            text = text,
            modifier = Modifier,
            color = Color.Black,
            fontSize = 20.sp,
            fontFamily = FontFamily.SansSerif
        )
        Text(
            text = goalText,
            modifier = Modifier.padding(2.dp),
            color = Color.Black,
            fontSize = 24.sp,
            fontFamily = FontFamily.SansSerif

        )
    }
}