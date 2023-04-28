package com.santrucho.habilife.ui.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santrucho.habilife.R

@Composable
fun StatsSection(actualHabits: String, habitCount: String, actualGoals: String, goalCount: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        InfoBox(title = stringResource(id = R.string.current_habits), value = actualHabits, 0.5f)
        InfoBox(title = stringResource(id = R.string.completed_habits), value = habitCount, 1f)
    }
    Spacer(modifier = Modifier.padding(8.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        InfoBox(title = stringResource(id = R.string.current_goals), value = actualGoals, 0.5f)
        InfoBox(title = stringResource(id = R.string.completed_goals), value = goalCount, 1f)
    }
}

@Composable
fun InfoBox(title: String, value: String, width: Float) {

    Card(
        modifier = Modifier
            .fillMaxWidth(width)
            .height(200.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(4.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                fontSize = 22.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(top = 2.dp))
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .size(102.dp)
                    .background(MaterialTheme.colors.secondary, shape = CircleShape)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .size(92.dp)
                        .background(Color.White, shape = CircleShape)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = value, fontSize = 48.sp)
                }
            }
        }
    }
}