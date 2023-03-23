package com.santrucho.habilife.ui.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatsSection(actualHabits: String,habitCount: String,actualGoals:String,goalCount:String) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoBox(title = "Habitos actuales", value = actualHabits, 0.5f)
            InfoBox(title = "Habitos completados", value = habitCount, 1f)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoBox(title = "Objetivos actuales", value = actualGoals, 0.5f)
            InfoBox(title = "Objetivos completados", value = goalCount, 1f)
        }
}

@Composable
fun InfoBox(title: String, value: String, width: Float) {

        Card(
            modifier = Modifier
                .fillMaxWidth(width)
                .height(200.dp)
                .padding(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
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
                Text(text = value, fontSize = 48.sp, fontWeight = FontWeight.W400)
            }
    }
}