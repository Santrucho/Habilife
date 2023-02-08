package com.santrucho.habilife.ui.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.ui.habits.HabitCard
import com.santrucho.habilife.ui.ui.habits.HabitList
import com.santrucho.habilife.ui.ui.habits.HabitUI
import com.santrucho.habilife.ui.utils.Resource

@Composable
fun HomeHabitList(habitViewModel: HabitViewModel) {

    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = 3.dp,
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp, 0.dp)
                    .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Habitos del dia",
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.wrapContentWidth(Alignment.Start),
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp
                )
                TextButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = "Ver todos",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.wrapContentWidth(Alignment.End),
                        textAlign = TextAlign.End,
                        fontSize = 12.sp
                    )
                }
            }
            HabitList(habitViewModel = habitViewModel)
        }
    }
}