package com.albatech.casadei.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.albatech.casadei.core.auth.AuthPrefsKeys
import com.albatech.casadei.core.auth.authDataStore
import com.albatech.casadei.ui.screens.HomeScreen
import com.albatech.casadei.ui.screens.LoginScreen
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

object Routes {
    const val login = "auth/login"
    const val home = "home"
}

@Composable
fun AppNav(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current

    // token SELALU String, default "" kalau gak ada
    val token by context.authDataStore.data
        .catch { e ->
            if (e is IOException) emit(emptyPreferences()) else throw e
        }
        .map { prefs ->
            prefs[AuthPrefsKeys.ACCESS_TOKEN] ?: ""
        }
        .collectAsState(initial = "")

    // optional: kalau kamu masih mau loading state tipis-tipis,
    // bisa pakai flag lain. Tapi buat simple case, langsung aja.
    val startDestination = if (token.isNotBlank()) Routes.home else Routes.login

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.login) {
            LoginScreen(
                onSuccess = {
                    navController.navigate(Routes.home) {
                        popUpTo(Routes.login) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.home) {
            val scope = rememberCoroutineScope()

            HomeScreen(
                onLogout = {
                    scope.launch {
                        context.authDataStore.edit { prefs ->
                            prefs.remove(AuthPrefsKeys.ACCESS_TOKEN)
                        }

                        navController.navigate(Routes.login) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}
