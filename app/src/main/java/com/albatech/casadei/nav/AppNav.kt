package com.albatech.casadei.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.albatech.casadei.ui.screens.HomeScreen
import com.albatech.casadei.ui.screens.LoginScreen

object Routes {
    const val Login = "auth/login"
    const val Home = "home"
}

@Composable
fun AppNav(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Routes.Login) {

        composable(Routes.Login) {
            LoginScreen(
                onSuccess = {
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.Login) { inclusive = true }   // bersihin backstack
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.Home) {
            HomeScreen(
                onLogout = {
                    // contoh balik ke login kalau mau
                    navController.navigate(Routes.Login) {
                        popUpTo(0) { inclusive = true } // wipe semua
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
