package com.dantruong.facebooknavpractice

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.navDeepLink
import com.dantruong.facebooknavpractice.data.Shortcut
import com.dantruong.facebooknavpractice.data.getRandomItems
import kotlinx.coroutines.launch
import com.dantruong.facebooknavpractice.ui.theme.FacebookNavPracticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FacebookNavPracticeTheme {
                val navController = rememberNavController()

                FbScaffold(navController = navController)
            }
        }
    }
}

@Composable
fun FbScaffold(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val randomItems = remember { mutableStateOf(getRandomItems(10)) }
    val shortCut = remember { mutableStateOf(Shortcut.getShortcuts()) }

    val onDrawerIconClick: () -> Unit = {
        scope.launch { drawerState.open() }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                NavigationDrawerScreen(navController = navController, randomItems.value, shortCut.value)
            }
        }
    ) {
        Scaffold(
            bottomBar = { FbBottomNav(navController = navController, onDrawer = onDrawerIconClick) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Destination.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Destination.Home.route) { HomeScreen(navController) }
                composable(Destination.Notification.route) { NotificationScreen(navController) }
                composable(
                    Destination.Detail.route,
                    deepLinks = listOf(
                        navDeepLink { uriPattern = "https://www.fblikeapp.com/{itemId}" }
                    )
                ) {
                    val itemId = it.arguments?.getString("itemId")
                    if (itemId == null) {
                        Toast.makeText(context, "Must have id", Toast.LENGTH_SHORT).show()
                    } else {
                        ItemDetail(itemId.toInt())
                    }
                }
            }
        }
    }
}






