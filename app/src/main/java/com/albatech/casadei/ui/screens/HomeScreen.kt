package com.albatech.casadei.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLogout: () -> Unit = {}
) {
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Home") }) }
    ) { pad ->
        Box(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Selamat datang, kamu resmi lolos dari halaman Login.", style = MaterialTheme.typography.titleLarge)
                Button(onClick = onLogout) { Text("Logout (dummy)") }
            }
        }
    }
}
