package com.santrucho.habilife.ui.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun HabitStats(habitCount:String){
    Column(modifier = Modifier.fillMaxWidth()){
        Text(text = "Habitos completados", fontSize = 24.sp, fontWeight = FontWeight.W400)
        Text(text = habitCount,fontSize = 24.sp, fontWeight = FontWeight.W400)
    }
}