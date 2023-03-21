package com.santrucho.habilife.ui.ui.habits.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.himanshoe.kalendar.Kalendar
import com.himanshoe.kalendar.color.KalendarThemeColor
import com.himanshoe.kalendar.component.day.config.KalendarDayColors
import com.himanshoe.kalendar.model.KalendarType

@Composable
fun CalendarView(){

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

            val background = MaterialTheme.colors.background
            val dayBackgroundColor = MaterialTheme.colors.secondary
            val headerTextColor = MaterialTheme.colors.primary
            val textColor = MaterialTheme.colors.primary
            val selectedColor = MaterialTheme.colors.primary

            Kalendar(kalendarDayColors = KalendarDayColors(textColor,selectedColor),kalendarThemeColor = KalendarThemeColor(background,dayBackgroundColor,headerTextColor), kalendarType = KalendarType.Oceanic)
        }
    }
}