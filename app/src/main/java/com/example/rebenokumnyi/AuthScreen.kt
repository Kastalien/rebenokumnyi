package com.example.rebenokumnyi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.rebenokumnyi.data.AppData
import com.example.rebenokumnyi.data.Roles
import com.example.rebenokumnyi.data.currentRole
import com.example.rebenokumnyi.data.saveGroupAndRole
import com.example.rebenokumnyi.ui.theme.appTypography
import com.example.rebenokumnyi.ui.theme.md_theme_light_surfaceTint
import com.firebase.ui.auth.AuthUI
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

@Composable
fun AuthScreen(
    onChangeAccount: (() -> Unit) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight(0.7F),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var isAuth by rememberSaveable { mutableStateOf(AppData.isAuth()) }
        var currentRoleScreen by remember { mutableStateOf(currentRole.role) }
        var userName by remember { mutableStateOf(currentRole.name) }
        if (isAuth) {
            Text(
                style = appTypography.labelMedium, text = stringResource(
                    R.string.user_name
                )
            )
            Text(
                text = userName, color = md_theme_light_surfaceTint
            )
            Spacer(Modifier.height(10.dp))
            if (currentRoleScreen != Roles.UNKNOWN) {
                Text(
                    text = when (currentRoleScreen) {
                        Roles.ADMIN -> "Администратор приложения"
                        Roles.PARENTUSER -> "Родитель ученика"
                        Roles.TEACHER -> "Преподаватель"
                        else -> ""
                    }, color = md_theme_light_surfaceTint
                )
            }
            Spacer(Modifier.height(10.dp))
            if (currentRoleScreen == Roles.UNKNOWN) {
                Text(
                    style = appTypography.labelMedium,
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.awayt_registration)
                )
            }
             if (currentRoleScreen == Roles.UNKNOWN) {
                Spacer(Modifier.height(10.dp))
                Button(onClick = {
                    onChangeAccount { currentRoleScreen = currentRole.role }
                }) { Text(text = stringResource(R.string.update_registration)) }
            }
            Spacer(Modifier.height(10.dp))
            Button(
                onClick = {
                    FirebaseAuth.getInstance().addAuthStateListener {
                        isAuth = (Firebase.auth.currentUser != null)
                        onChangeAccount {}
                    }
                    FirebaseAuth.getInstance().signOut()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
                    .padding(bottom = 10.dp)
            ) {
                Text("Выйти из аккаунта")
            }
        } else {
            Text("Вы не авторизованы!")
            Spacer(Modifier.height(40.dp))
            Button(
                onClick = {
                    FirebaseAuth.getInstance().addAuthStateListener {
                        isAuth = (Firebase.auth.currentUser != null)
                        onChangeAccount {
                            userName = currentRole.name
                            currentRoleScreen = currentRole.role
                        }
                    }
                    if (Firebase.auth.currentUser == null) {
                        val signInIntent = AuthUI.getInstance().createSignInIntentBuilder()
                            .setLogo(R.drawable.logo).setAvailableProviders(
                                listOf(
                                    AuthUI.IdpConfig.EmailBuilder().build(),
                                    AuthUI.IdpConfig.GoogleBuilder().build(),
                                )
                            ).build()
                        AppData.context.signIn.launch(signInIntent)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
                    .padding(bottom = 10.dp)
            ) {
                Text("Авторизоваться/создать аккаунт")
            }
        }
        //FOR DEBUGGING AND DEVELOPMENT
//        Button(onClick = {
//            saveGroupAndRole()
//        }) { Text("Загрузить в FireBase") }
        //--------------------------------------------------
    }
}