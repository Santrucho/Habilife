package com.santrucho.habilife.ui.ui.goals.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santrucho.habilife.R
import com.santrucho.habilife.ui.presentation.GoalViewModel

@Composable
fun NewGoalFields(goalViewModel: GoalViewModel){

//Set the fields to show and fill for create a new habit}

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        Card(
            shape = MaterialTheme.shapes.medium,
            elevation = 3.dp,
            backgroundColor = MaterialTheme.colors.background,
            modifier = Modifier.padding(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text("Personalice su objetivo",fontSize = 20.sp, color = Color.Black)
                OutlinedTextField(
                    value = goalViewModel.titleValue.value,
                    onValueChange = {
                        goalViewModel.titleValue.value = it
                        goalViewModel.validateTitle()
                    },
                    label = { Text(text = "Descripcion") },
                    isError = goalViewModel.isTitleValid.value,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    shape = CircleShape,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.Black,
                        focusedBorderColor = Color.Blue,
                        unfocusedBorderColor = Color.Blue,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Blue)
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = goalViewModel.titleErrMsg.value,
                    fontSize = 14.sp,
                    color = Color.Red
                )

                OutlinedTextField(
                    value = goalViewModel.descriptionValue.value,
                    onValueChange = {
                        goalViewModel.descriptionValue.value = it
                        goalViewModel.validateDescription()
                    },
                    label = { Text(text = "Descripcion") },
                    isError = goalViewModel.isDescriptionValid.value,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    shape = CircleShape,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.Black,
                        focusedBorderColor = Color.Blue,
                        unfocusedBorderColor = Color.Blue,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Blue
                    )
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = goalViewModel.descriptionErrMsg.value,
                    fontSize = 14.sp,
                    color = Color.Red
                )

            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
    }
}