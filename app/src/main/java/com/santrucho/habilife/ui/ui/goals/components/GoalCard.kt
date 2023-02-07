package com.santrucho.habilife.ui.ui.goals.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santrucho.habilife.ui.data.model.Goals


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GoalCard(goal: Goals,onDelete:(Goals) -> Unit) {

    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(targetValue = if (expandedState) 180f else 0f)

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
            .fillMaxWidth()
            .animateContentSize (animationSpec = tween(
            durationMillis = 300,
            easing = LinearOutSlowInEasing
    )),
        elevation = 3.dp,
        onClick = {expandedState = !expandedState}
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                goal.title.let { title ->
                    Text(
                        text = title,
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .wrapContentHeight(Alignment.Top),
                        color = Color.DarkGray,
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
                goal.description.let { description ->
                    Text(
                        text = description,
                        modifier = Modifier
                            .wrapContentHeight(Alignment.Top),
                        color = Color.DarkGray,
                        fontSize = 25.sp
                    )
                }
                goal.release_date.let { release_date ->
                    Text(
                        text = release_date,
                        modifier = Modifier
                            .wrapContentHeight(Alignment.Bottom),
                        color = Color.DarkGray,
                        fontSize = 25.sp
                    )
                }
                Button(
                    onClick = {
                        onDelete(goal)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(50.dp),
                ) {
                    Text(
                        text = "Delete"
                    )
                }
            }
        }
    }
}