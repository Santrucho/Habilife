package com.santrucho.habilife.ui.ui.habits

import android.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santrucho.habilife.ui.data.model.Habit

@Composable
fun HabitCard(habit: Habit) {
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
            habit.title.let { title ->
                Text(
                    text = title,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .wrapContentHeight(Alignment.Top),
                    color = androidx.compose.ui.graphics.Color.Companion.DarkGray,
                    fontSize = 25.sp
                )
            }
            habit.description.let { description ->
                Text(
                    text = description,
                    modifier = Modifier
                        .wrapContentHeight(Alignment.Top),
                    color = androidx.compose.ui.graphics.Color.Companion.DarkGray,
                    fontSize = 25.sp
                )
            }
            habit.frequently.let { frequently ->
                Text(
                    text = frequently,
                    modifier = Modifier
                        .wrapContentHeight(Alignment.Bottom),
                    color = androidx.compose.ui.graphics.Color.Companion.DarkGray,
                    fontSize = 25.sp
                )
            }
            habit.id.let { id ->
                Text(
                    text = id,
                    modifier = Modifier
                        .wrapContentHeight(Alignment.Top),
                    color = androidx.compose.ui.graphics.Color.Companion.DarkGray,
                    fontSize = 25.sp
                )
            }
        }
    }
}