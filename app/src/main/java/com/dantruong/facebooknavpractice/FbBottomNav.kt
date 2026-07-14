package com.dantruong.facebooknavpractice
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.navigation.NavHostController

@Composable
fun FbBottomNav(modifier: Modifier = Modifier, navController: NavHostController, onDrawer: () -> Unit) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination
    NavigationBar(modifier = modifier) {
        // home screen
        NavigationBarItem(
            selected = currentDestination?.route == Destination.Home.route,
            onClick = { navController.navigate(Destination.Home.route) },
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text(text = Destination.Home.route) }
        )

        // notification
        NavigationBarItem(
            selected = currentDestination?.route == Destination.Notification.route,
            onClick = { navController.navigate(Destination.Notification.route) },
            icon = { Icon(Icons.Default.Notifications, contentDescription = null) }, // You can change this to Notification icon later
            label = { Text(text = Destination.Notification.route) }
        )

        // menu
        NavigationBarItem(
            selected = false,
            onClick = onDrawer,
            icon = { Icon(Icons.Default.Menu, contentDescription = null) },
            label = { Text(text = "Menu") }
        )
    }
}