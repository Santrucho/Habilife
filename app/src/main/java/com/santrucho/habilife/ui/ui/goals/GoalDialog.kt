package com.santrucho.habilife.ui.ui.goals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)){
        Card(shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth().padding(6.dp), elevation = 8.dp){
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Box(modifier = Modifier.wrapContentHeight()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        GlideImage(
                            model = goal.image,
                            contentDescription = "background image",
                            modifier = Modifier.fillMaxWidth().height(90.dp).padding(4.dp).clip(RoundedCornerShape(10.dp)),
                        )
                    }
                }

                Text(
                    text = goal.title,
                    modifier = Modifier.padding(8.dp),
                    fontSize = 24.sp,
                    color = Color.Black
                )

                Text(
                    text = goal.description,
                    modifier = Modifier.padding(8.dp),
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = "Fecha limite:",
                        modifier = Modifier
                            .padding(8.dp),
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                    Text(
                        text = goal.release_date,
                        modifier = Modifier
                            .padding(8.dp),
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                }

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