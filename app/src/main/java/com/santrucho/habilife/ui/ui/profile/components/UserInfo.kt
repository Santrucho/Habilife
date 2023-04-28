package com.santrucho.habilife.ui.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.analytics.FirebaseAnalytics
import com.santrucho.habilife.R
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.util.LogBundle

@Composable
fun UserInfo(
    name: String,
    email: String,
    onLogout: () -> Unit,
    navController: NavController,
) {
    Card(
        modifier = Modifier.padding(12.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(4.dp),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Spacer(modifier = Modifier.padding(32.dp))
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = R.drawable.logo,
                    contentDescription = "profile image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(82.dp)
                        .height(82.dp)
                        .padding(4.dp)
                )
                Column(
                    modifier = Modifier.wrapContentWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "${stringResource(id = R.string.profile)} ",
                        fontWeight = FontWeight.W800,
                        fontSize = 24.sp
                    )
                    Row(
                        modifier = Modifier.wrapContentWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${stringResource(id = R.string.username_text)} ",
                            fontWeight = FontWeight.W300,
                            fontSize = 16.sp
                        )
                        Text(
                            text = name, fontWeight = FontWeight.W600, fontSize = 24.sp
                        )
                    }
                    Row(
                        modifier = Modifier.wrapContentWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${stringResource(id = R.string.email_text)} ",
                            fontWeight = FontWeight.W300,
                            fontSize = 16.sp
                        )
                        Text(
                            text = email, fontWeight = FontWeight.W500, fontSize = 16.sp
                        )
                    }
                }
            }
            LogOut(onLogout = onLogout, navController = navController)
        }
    }
}

@Composable
fun LogOut(
    onLogout: () -> Unit,
    navController: NavController,
) {
    val context = LocalContext.current
    val firebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    val openDialog = remember { mutableStateOf(false) }
    IconButton(onClick = {
        openDialog.value = true
        LogBundle.logBundleAnalytics(firebaseAnalytics, "Sign Out", "sign_out_pressed")
    }, modifier = Modifier.size(48.dp)) {
        Icon(
            painter = painterResource(id = R.drawable.ic_log_out_svgrepo_com),
            contentDescription = "log out",
            tint = MaterialTheme.colors.primary
        )
    }
    if (openDialog.value) {
        AlertDialog(onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Cerrar sesion", fontSize = 22.sp) },
            text = { Text(text = "Estas seguro que desea cerrar sesion?", fontSize = 18.sp) },
            confirmButton = {
                TextButton(onClick = {
                    onLogout()
                    navController.navigate(Screen.LoginScreen.route)
                    LogBundle.logBundleAnalytics(
                        firebaseAnalytics,
                        "Sign Out Confirm",
                        "confirm_sign_out_pressed"
                    )
                }) {
                    Text("Confirmar", color = Color.Black, fontSize = 18.sp)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    openDialog.value = false
                    LogBundle.logBundleAnalytics(
                        firebaseAnalytics,
                        "Sign Out Cancel",
                        "cancel_sign_out_pressed"
                    )
                }) {
                    Text("Cancelar", color = Color.Black, fontSize = 18.sp)
                }
            })
    }
}
