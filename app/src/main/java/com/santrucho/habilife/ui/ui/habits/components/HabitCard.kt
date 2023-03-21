package com.santrucho.habilife.ui.ui.habits.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.util.iconHelper
import com.santrucho.habilife.ui.util.typeHelper
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*


//Set the visualization and the way in which each habit will be displayed
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HabitCard(
    habit: Habit, onDelete: (Habit) -> Unit, viewModel: HabitViewModel
) {

    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(targetValue = if (expandedState) 180f else 0f)

    val isEnabled = habit.frequently.any{ day ->
        day.equals(LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale("es","ARG")),ignoreCase = true)
    }


    Column(modifier = Modifier.wrapContentSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Checkbox(
                modifier = Modifier
                    .wrapContentHeight(Alignment.Top)
                    .size(32.dp)
                    .padding(2.dp),
                checked = habit.completed,
                onCheckedChange = { isChecked ->
                   viewModel.onCompleted(habit, isChecked)
                },
                colors = CheckboxDefaults.colors(checkedColor = Color.Blue),
                enabled = isEnabled
            )
            Card(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(8.dp, 2.dp)
                    .fillMaxWidth()
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = LinearOutSlowInEasing
                        )
                    ),
                elevation = 3.dp,
                onClick = { expandedState = !expandedState },
                backgroundColor = typeHelper(habit.type)
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(2.dp)
                            .wrapContentSize()){

                            Icon(painterResource(id = iconHelper(habitType = habit.type)),
                            contentDescription = "Selected icon",
                            modifier = Modifier.wrapContentSize().padding(4.dp))
                            Text(
                                text = habit.title,
                                modifier = Modifier
                                    .fillMaxWidth(0.85f)
                                    .wrapContentHeight(Alignment.Top)
                                    .wrapContentWidth(Alignment.CenterHorizontally),
                                color = White,
                                fontSize = 25.sp
                            )
                        }
                        IconButton(
                            modifier = Modifier
                                .weight(1f)
                                .alpha(ContentAlpha.medium)
                                .rotate(rotationState),
                            onClick = {
                                expandedState = !expandedState
                            }) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Drop-Down Arrow"
                            )
                        }
                    }
                    if (expandedState) {
                        Text(
                            text = habit.description,
                            modifier = Modifier
                                .wrapContentHeight(Alignment.Top),
                            color = White,
                            fontSize = 16.sp
                        )
                        Text(
                            text = habit.timePicker,
                            modifier = Modifier
                                .wrapContentHeight(Alignment.Top),
                            color = White,
                            fontSize = 16.sp
                        )

                        Text(
                            text = habit.frequently.joinToString(),
                            modifier = Modifier
                                .wrapContentHeight(Alignment.Bottom)
                                .wrapContentWidth(Alignment.Start),
                            color = White,
                            fontSize = 12.sp
                        )
                        Row(
                            modifier = Modifier
                                .wrapContentWidth(Alignment.End),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {

                            IconButton(
                                modifier = Modifier
                                    .weight(1f)
                                    .alpha(ContentAlpha.medium)
                                    .wrapContentWidth(Alignment.End),
                                onClick = {
                                    onDelete(habit)
                                }) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Drop-Down Arrow",
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}