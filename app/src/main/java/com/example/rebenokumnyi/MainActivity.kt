package com.example.rebenokumnyi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose.RebenokumnyiTheme
import com.example.rebenokumnyi.adminscreens.AdminGroupScreen
import com.example.rebenokumnyi.adminscreens.AdminStudentScreen
import com.example.rebenokumnyi.adminscreens.AdminTeacherScreen
import com.example.rebenokumnyi.components.URTabRow
import com.example.rebenokumnyi.data.AboutPage
import com.example.rebenokumnyi.data.AdminGroup
import com.example.rebenokumnyi.data.AdminStudent
import com.example.rebenokumnyi.data.AdminTeacher
import com.example.rebenokumnyi.data.AppData
import com.example.rebenokumnyi.data.AppData.isAuth
import com.example.rebenokumnyi.data.AuthPage
import com.example.rebenokumnyi.data.ParentChat
import com.example.rebenokumnyi.data.ParentJournal
import com.example.rebenokumnyi.data.ParentPaydata
import com.example.rebenokumnyi.data.ParentSchedule
import com.example.rebenokumnyi.data.Roles
import com.example.rebenokumnyi.data.TeacherChat
import com.example.rebenokumnyi.data.TeacherJournal
import com.example.rebenokumnyi.data.TeacherSchedule
import com.example.rebenokumnyi.data.adminTabRowScreens
import com.example.rebenokumnyi.data.currentRole
import com.example.rebenokumnyi.data.loadRole
import com.example.rebenokumnyi.data.notAuthTabRowScreens
import com.example.rebenokumnyi.data.parentTabRowScreens
import com.example.rebenokumnyi.data.teacherTabRowScreens
import com.example.rebenokumnyi.parentscreens.ParentChatScreen
import com.example.rebenokumnyi.parentscreens.ParentJournalScreen
import com.example.rebenokumnyi.parentscreens.ParentPaymentScreen
import com.example.rebenokumnyi.parentscreens.ParentScheduleScreen
import com.example.rebenokumnyi.teacherscreens.TeacherChatScreen
import com.example.rebenokumnyi.teacherscreens.TeacherJournalScreen
import com.example.rebenokumnyi.teacherscreens.TeacherScheduleScreen
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : ComponentActivity() {

    val signIn: ActivityResultLauncher<Intent> =
        registerForActivityResult(FirebaseAuthUIActivityResultContract(), ::onSignInResult)

    var onSingIn: () -> Unit = {}

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            Log.d("urlog", "Sign in successful!")
            onSingIn()
        } else {
            val response = result.idpResponse
            if (response == null) {
                Log.w("urlog", "Sign in canceled")
            } else {
                Log.w("urlog", "Sign in error", response.error)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppData.initAuth(this)
        setContent {
            InitialLoading()
        }
    }
}

@Composable
fun InitialLoading() {
    var isLoaded by remember { mutableStateOf(false) }
    if (isLoaded)
        MainApp()
    else {
        RebenokumnyiTheme(dynamicColor = false) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(stringResource(R.string.server_connection))
                CircularProgressIndicator()
            }
        }
    }
    DisposableEffect(Unit) {
        loadRole({ isLoaded = true })
        onDispose {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(
    modifier: Modifier = Modifier
) {
    RebenokumnyiTheme(dynamicColor = false) {
        var isAuth by remember { mutableStateOf(AppData.isAuth()) }
        var currentRoleScreen by remember { mutableStateOf(currentRole.role) }
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        //TODO depend of auth
        val startDestination = AuthPage
        val screens = if (!isAuth) notAuthTabRowScreens
        else {
            when (currentRoleScreen) {
                Roles.PARENTUSER -> parentTabRowScreens
                Roles.ADMIN -> adminTabRowScreens
                Roles.TEACHER -> teacherTabRowScreens
                else -> notAuthTabRowScreens
            }
        }
        val currentScreen =
            screens.find { it.route == currentDestination?.route } ?: startDestination
        var openExitDialog by remember { mutableStateOf(false) }
        //TODO depend of auth
        var requestedDestination by remember { mutableStateOf(startDestination.route) }
        Scaffold(topBar = {
            //TODO depend of auth
            URTabRow(
                allScreens = screens, onTabSelected = { newScreen ->
                    run {
                        navController.navigateSingleTopTo(route = newScreen.route)
                    }
                }, currentScreen = currentScreen
            )
        }) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = startDestination.route,
                modifier = modifier.padding(innerPadding)
            ) {
                composable(route = ParentSchedule.route) {
                    ParentScheduleScreen()
                }
                composable(route = ParentJournal.route) {
                    ParentJournalScreen()
                }
                composable(route = ParentChat.route) {
                    ParentChatScreen()
                }
                composable(route = ParentPaydata.route) {
                    ParentPaymentScreen()
                }
                composable(route = TeacherSchedule.route) {
                    TeacherScheduleScreen()
                }
                composable(route = TeacherJournal.route) {
                    TeacherJournalScreen()
                }
                composable(route = TeacherChat.route) {
                    TeacherChatScreen()
                }
                composable(route = AdminTeacher.route) {
                    AdminTeacherScreen(){
                        navController.navigateWithStringParameter("adminstudent", it)
                    }
                }
                composable(route = AdminGroup.route) {
                    AdminGroupScreen()
                }
                composable(route = AuthPage.route) {
                    AuthScreen {
                        if (isAuth()) {
                            GlobalScope.launch {
                                if (Firebase.auth.currentUser?.displayName==null)
                                    Firebase.auth.currentUser?.reload()
                                loadRole {
                                    currentRoleScreen = currentRole.role
                                    it()
                                }
                            }
                        }
                        isAuth = isAuth()
                    }
                }
                composable(route = AboutPage.route) {
                    AboutScreen()
                }
                composable(
                    route = AdminStudent.route,
                    arguments =  listOf(navArgument("userId") { type = NavType.StringType })
                ) {navBackStackEntry ->
                    val parentId =
                        navBackStackEntry.arguments?.getString("userId")
                    AdminStudentScreen(parentId?:""){
                        navController.navigateSingleTopTo("adminteacher")
                    }
                }
            }
        }
    }
}

fun NavHostController.navigateWithStringParameter(route: String, param: String) {
    this.navigateSingleTopTo("${route}/$param")
}

fun NavHostController.navigateSingleTopTo(route: String) = this.navigate(route) {
    launchSingleTop = true
}