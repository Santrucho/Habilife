package com.santrucho.habilife.ui.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.SignUpViewModel

@Composable
fun UserInfo(
    name: String,
    email: String,
    onLogout:() -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.padding(8.dp),
            elevation = 8.dp,
            shape = RoundedCornerShape(4.dp)
        ) {
            LogOut(onLogout = onLogout,navController = navController)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = "https://firebasestorage.googleapis.com/v0/b/habilife-2bba3.appspot.com/o/Habi(1).png?alt=media&token=8cbe8c2c-ee97-4f80-b8ba-e130fcce7379",
                    contentDescription = "profile image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(68.dp)
                        .height(68.dp)
                        .padding(4.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = name, fontWeight = FontWeight.W600, fontSize = 24.sp
                )
                Text(
                    text = email, fontWeight = FontWeight.W600, fontSize = 24.sp
                )
            }
        }
    }
}

@Composable
fun LogOut(
    onLogout: () -> Unit,
    navController: NavController
) {
    val openDialog = remember { mutableStateOf(false) }
    IconButton(onClick = {
        openDialog.value = true
    }) {
        Icon(imageVector = Icons.Outlined.Close, contentDescription = "log out")
    }
    if (openDialog.value) {
        AlertDialog(onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Cerrar sesion", fontSize = 22.sp) },
            text = {Text(text= "Estas seguro que desea cerrar sesion?",fontSize = 18.sp)} ,
            confirmButton = {
                TextButton(onClick = {
                    onLogout()
                    navController.navigate(Screen.LoginScreen.route)
                }) {
                    Text("Confirm", color = Color.Black,fontSize = 18.sp)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    openDialog.value = false
                }) {
                    Text("Cancel", color = Color.Black,fontSize = 18.sp)
                }
            })
    }
}
