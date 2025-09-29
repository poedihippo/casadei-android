package com.albatech.casadei

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import com.albatech.casadei.ui.theme.AppTheme
import androidx.compose.material3.MaterialTheme
import com.albatech.casadei.ui.screens.LoginScreen
import com.albatech.casadei.nav.AppNav

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // ikut system dark mode, dan dynamic color on (Android 12+)
            AppTheme(darkTheme = isSystemInDarkTheme(), dynamicColor = true) {
                // Surface biar background ngikut tema (important!)
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNav()
                }
            }
        }
    }
}
