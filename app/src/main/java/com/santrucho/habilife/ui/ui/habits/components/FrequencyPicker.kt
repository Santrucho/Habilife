package com.santrucho.habilife.ui.ui.habits.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santrucho.habilife.R


//Set the box to choose the frequency for makes the habit
@Composable
fun FrequencyPicker(itemList: List<String>, onDaySelected:(List<String>) -> Unit)  {

    var selectedDays by remember { mutableStateOf(emptyList<String>()) }
    var selectAll by remember { mutableStateOf(false) }

    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = 3.dp,
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp, 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Frecuencia del habito", fontSize = 20.sp, color = Color.Black)

                Row(
                    modifier = Modifier
                        .padding(8.dp, 0.dp)
                        .wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text("All:", color = Color.Black)
                    IconButton(
                        modifier = Modifier
                            .wrapContentWidth()
                            .alpha(ContentAlpha.medium),
                        onClick = {
                            selectAll = !selectAll
                            selectedDays = if (selectAll) {
                                selectedDays + itemList
                            } else {
                                selectedDays - itemList
                            }
                            onDaySelected(selectedDays)
                        }) {
                        Icon(
                            painter = if (selectAll) painterResource(R.drawable.ic_check_square) else painterResource(id=R.drawable.ic_checkbox_square),
                            contentDescription = "check",
                            tint = if (selectAll) Color.Blue else Color.Black
                        )
                    }
                }
            }
            LazyRow(
                modifier = Modifier
                    .padding(8.dp, 8.dp)
                    .fillMaxWidth()
            ) {
                items(itemList.size) { index ->
                    MyChip(
                        title = itemList[index],
                        selected = selectedDays.contains(itemList[index]),
                        onSelected = { isSelected ->
                            selectedDays = if (isSelected) {
                                selectedDays + itemList[index]
                            } else {
                                selectedDays - itemList[index]
                            }
                            onDaySelected(selectedDays)
                        },
                        colorBackground = MaterialTheme.colors.secondaryVariant
                    )
                }
            }
        }
    }
}

//Set each element which represent a day of week to mark and select for the frequency in the habit
@Composable
fun MyChip(
    title: String,
    selected: Boolean,
    onSelected: (Boolean) -> Unit,
    colorBackground : Color
) {

    val background = if (selected) MaterialTheme.colors.primary else colorBackground
    val contentColor = if (selected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .padding(end = 10.dp)
            .height(35.dp)
            .clip(CircleShape)
            .background(background)
            .clickable(
                onClick = {
                    onSelected(!selected)
                }
            )
    ) {
        Row(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            AnimatedVisibility(visible = selected) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "check",
                    tint = Color.White
                )
            }

            Text(text = title, color = contentColor, fontSize = 16.sp)

        }
    }

}
