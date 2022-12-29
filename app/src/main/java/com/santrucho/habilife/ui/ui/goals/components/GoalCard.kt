package com.santrucho.habilife.ui.ui.goals.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santrucho.habilife.ui.data.model.Goals


@Composable
fun GoalCard(goal: Goals) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
            .fillMaxWidth(),
        elevation = 3.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp)
        ) {
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
        }
    }
}