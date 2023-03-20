package com.santrucho.habilife.ui.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.santrucho.habilife.R

//In case there doesn't exist any Habit or Goals to make that day, show a Empty Message to notify the user
@Composable
fun EmptyMessage(text: String) {

    AsyncImage(
        model = R.drawable.ic_undraw_empty_re_opql,
        contentDescription = "empty icon",
        contentScale = ContentScale.Crop,
        alpha = 0.6f,
        modifier = Modifier
            .width(122.dp)
            .height(122.dp),
    )
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (text == "objetivo") {
            Text(
                text = "Al parecer no tienes ningun $text\n creado actualmente",
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray,
                modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
            Text(
                text = "Haz click aqui y empieza el primero!\n",
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray,
                modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally),
                textAlign = TextAlign.Start,
                fontSize = 16.sp
            )
        } else {
            Text(
                text = "Al parecer no tienes ningun $text \npara realizar hoy",
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray,
                modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
            Text(
                text = "Crea nuevos habitos aqui",
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray,
                modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        }
    }
}
