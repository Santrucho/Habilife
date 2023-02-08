package com.santrucho.habilife.ui.ui.goals.components

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
                start = 4.dp,
                end = 4.dp,
                top = 2.dp,
                bottom = 2.dp
            )
            .fillMaxWidth()
            .animateContentSize (animationSpec = tween(
            durationMillis = 300,
            easing = LinearOutSlowInEasing
    )),
        elevation = 3.dp,
        onClick = {expandedState = !expandedState},
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = goal.title,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .wrapContentHeight(Alignment.Top),
                    color = Color.Black,
                    fontSize = 25.sp
                )
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(ContentAlpha.medium)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            if (expandedState) {
                Text(
                    text = goal.description,
                    modifier = Modifier
                        .wrapContentHeight(Alignment.Top),
                    color = Color.White,
                    fontSize = 25.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween,modifier=Modifier.fillMaxWidth()) {

                    Text(
                        text = goal.release_date,
                        modifier = Modifier
                            .wrapContentHeight(Alignment.Bottom),
                        color = Color.White,
                        fontSize = 25.sp
                    )
                    IconButton(
                        modifier = Modifier
                            .weight(1f)
                            .alpha(ContentAlpha.medium)
                            .wrapContentWidth(Alignment.End),
                        onClick = {
                            onDelete(goal)
                        }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Drop-Down Arrow"
                        )
                    }
                }
            }
        }
    }
}