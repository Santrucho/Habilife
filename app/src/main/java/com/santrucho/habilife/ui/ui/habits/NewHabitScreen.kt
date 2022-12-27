package com.santrucho.habilife.ui.ui.habits

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santrucho.habilife.R
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.utils.BackPressHandler

@Composable
fun NewHabitScreen( navController: NavController) {

    // onBack can be passed down as composable param and hoisted
    val onBack = { navController.navigate(BottomNavScreen.Habit.screen_route) }

    BackPressHandler(onBackPressed = onBack)

    Scaffold(
        topBar = { DetailsAppBar(onBack) }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.white))
            ) {
                NewHabitsFields()
                Spacer(modifier = Modifier.padding(16.dp))
                Column(modifier = Modifier.fillMaxWidth()){
                    Button(
                        onClick = { navController.navigate(BottomNavScreen.Habit.screen_route) },
                        modifier = Modifier.fillMaxWidth().padding(8.dp).fillMaxHeight(0.2f),
                        shape = CircleShape

                    ) {
                        Text("Guardar habito")
                    }
                }

            }
        }
    }


}

@Composable
fun DetailsAppBar(onBack: () -> Unit){
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.app_name),
                )
            }
        }
    )
}

@Composable
fun NewHabitsFields(){
    var titleValue by remember { mutableStateOf("") }
    var descriptionValue by remember { mutableStateOf("") }
    var frequencyValue by remember { mutableStateOf("") }

    TextField(
        value = titleValue,
        onValueChange = { titleValue = it },
        placeholder = { Text(text = "Habit title") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(1f),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )

    TextField(
        value = descriptionValue,
        onValueChange = { descriptionValue = it },
        label = { Text(text = "Descripcion") },
        placeholder = { Text(text = "Description") },
        modifier = Modifier.fillMaxWidth(1f).fillMaxHeight(0.5f),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
    Spacer(modifier = Modifier.padding(8.dp))

    TextField(
        value = frequencyValue,
        onValueChange = { frequencyValue = it },
        label = { Text(text = "Frecuencia") },
        placeholder = { Text(text = "frequency") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(1f).padding(8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}