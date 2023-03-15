package com.santrucho.habilife.ui.utils.helper

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse

@Composable
fun ProgressBarHelper(goal: GoalsResponse) {
    when (goal.type) {
        "Finance" -> {
            CustomLinearProgress(
                maxProgress = goal.amountGoal?.toFloat(),
                currentProgress = goal.amount?.toFloat(),
                valueType = "%",
                showValues = false
            )
        }
        "Training" -> {
            CustomLinearProgress(
                maxProgress = goal.kilometersGoal?.toFloat(),
                currentProgress = goal.kilometers?.toFloat(),
                valueType = "%",
                showValues = false
            )
        }
        "Academic" -> {
            CustomLinearProgress(
                maxProgress = goal.subjectList?.size?.toFloat(),
                currentProgress = goal.subjectApproved.size.toFloat(),
                valueType = "%",
                showValues = false
            )
        }
    }
}

@Composable
fun CustomLinearProgress(
    maxProgress: Float?,
    currentProgress: Float?,
    valueType: String = "",
    showValues: Boolean = false
) {

    if (showValues) {
        Row(
            modifier = Modifier
                .widthIn(min = 30.dp)
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${currentProgress?.toInt() ?: 0} $valueType",
                color = Color.Black,
                fontSize = 16.sp
            )
            Text(text = "${maxProgress?.toInt()} $valueType", color = Color.Black, fontSize = 16.sp)
        }
    } else {
        Row(
            modifier = Modifier
                .widthIn(min = 30.dp)
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "${(((currentProgress?.toInt() ?: 0) * 100) / (maxProgress ?: 1).toInt()) ?: 0} $valueType",
                color = Color.White,
                fontSize = 16.sp
            )

        }
    }
    //Progress Bar
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(12.dp)
    ) {
        // for the background of the ProgressBar
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.secondaryVariant)
        )
        // for the progress of the ProgressBar

        Box(
            modifier = Modifier
                .fillMaxWidth((currentProgress?.toInt() ?: 0) / (maxProgress ?: 1).toFloat())
                .fillMaxHeight()
                .clip(RoundedCornerShape(9.dp))
                .background(MaterialTheme.colors.primary)
                .animateContentSize()
        )
    }
}

