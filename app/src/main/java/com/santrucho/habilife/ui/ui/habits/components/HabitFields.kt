package com.santrucho.habilife.ui.ui.habits.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santrucho.habilife.R
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.theme.White

@Composable
fun HabitFields(habitViewModel: HabitViewModel) {

        OutlinedTextField(
            value = habitViewModel.titleValue.value,
            onValueChange = {
                habitViewModel.titleValue.value = it
                habitViewModel.validateTitle()
            },
            isError = habitViewModel.isTitleValid.value,
            label = { Text(text = "Nombre del habito") },
            placeholder = { Text(text = "Habit title") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = habitViewModel.titleErrMsg.value,
            fontSize = 14.sp,
            color = Color.Red
        )
        OutlinedTextField(
            value = habitViewModel.descriptionValue.value,
            onValueChange = {
                habitViewModel.descriptionValue.value = it
                habitViewModel.validateDescription()
            },
            isError = habitViewModel.isDescriptionValid.value,
            label = { Text(text = "Descripcion") },
            placeholder = { Text(text = "Description") },
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = habitViewModel.descriptionErrMsg.value,
            fontSize = 14.sp,
            color = Color.Red
        )
        OutlinedTextField(
            value = habitViewModel.frequencyValue.value,
            onValueChange = {
                habitViewModel.frequencyValue.value = it
                habitViewModel.validateFrequently()
            },
            isError = habitViewModel.isFrequentlyValid.value,
            label = { Text(text = "Frecuencia") },
            placeholder = { Text(text = "frequency") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = habitViewModel.frequentlyMsg.value,
            fontSize = 14.sp,
            color = Color.Red
        )
    }

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Categories(options: Array<String>) {

    var selectedOption by remember { mutableStateOf(options[0]) }
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                readOnly = true,
                value = selectedOption,
                onValueChange = { },
                label = { Text("Categories", color = Color.Black)},
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(textColor = Color.Black, focusedBorderColor = typeHelper(
                    habitType = selectedOption), focusedTrailingIconColor = typeHelper(habitType = selectedOption)),
                shape = CircleShape,
                modifier = Modifier
                    .fillMaxWidth(1f)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOption = selectionOption
                            expanded = false
                        },
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(4.dp)
                            .background(typeHelper(selectionOption), shape = CircleShape)
                    )
                    {
                        Text(text = selectionOption,fontSize = 24.sp, textAlign = TextAlign.Center, color = Color.Black)
                    }
                }
            }
        }
    }
}