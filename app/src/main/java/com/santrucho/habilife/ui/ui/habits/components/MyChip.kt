package com.santrucho.habilife.ui.ui.habits.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santrucho.habilife.ui.data.model.ItemList


//Set the box to choose the frequency for makes the habit
@Composable
fun FrequencyPicker() {

    val itemList: List<ItemList> =
        listOf(
            ItemList("Lun"),
            ItemList("Mar"),
            ItemList("Mier"),
            ItemList("Jue"),
            ItemList("Vier"),
            ItemList("Sab"),
            ItemList("Dom")
        )
    var selectedItems by remember { mutableStateOf(emptyList<ItemList>()) }
    var selectAll by remember { mutableStateOf(false) }

    Card(shape = MaterialTheme.shapes.medium,
        elevation = 3.dp,
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier.padding(4.dp)){
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
            Text("Frecuencia del habito:", fontSize = 20.sp, color = Color.Black)

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
                        selectedItems = if(selectAll) {
                            itemList
                        } else {
                            emptyList()
                        }

                    }) {
                    Icon(
                        imageVector = if(selectAll) Icons.Filled.Check else Icons.Filled.List,
                        contentDescription = "check",
                        tint = if(selectAll) Color.Black else Color.Black
                    )
                }

            }
        }
        LazyRow(
            modifier = Modifier
                .padding(8.dp,8.dp)
                .fillMaxWidth()
        ) {
            items(itemList) { it ->
                MyChip(
                    title = it.text,
                    selected = selectedItems.contains(it),
                    onSelected = { isSelected ->
                        selectedItems = if (isSelected) {
                            selectedItems + it
                        } else {
                            selectedItems - it
                        }
                    },
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
) {

    val background = if (selected) Color.Blue else Color.LightGray
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
