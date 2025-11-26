package com.albatech.casadei.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.albatech.casadei.core.auth.AuthPrefsKeys
import com.albatech.casadei.core.auth.authDataStore
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLogout: () -> Unit = {}
) {
    val context = LocalContext.current

    // bikin Flow<String> dari DataStore
    val tokenFlow = remember {
        context.authDataStore.data.map { prefs ->
            prefs[AuthPrefsKeys.ACCESS_TOKEN] ?: ""
        }
    }

    // collect jadi State biar bisa dipakai di UI
    val token by tokenFlow.collectAsState(initial = "")


    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Home") }) }
    ) { pad ->
        Box(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    "Selamat datang, kamu resmi lolos dari halaman Login.",
                    style = MaterialTheme.typography.titleLarge
                )

                if (token.isNotBlank()) {
                    Text(
                        text = "Token aktif: $token",
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Text(
                        text = "Token nggak kebaca / kosong",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Button(onClick = onLogout) {
                    Text("Logout (dummy)")
                }
            }
        }
    }
}
