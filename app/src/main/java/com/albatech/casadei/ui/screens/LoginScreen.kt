@file:OptIn(ExperimentalMaterial3Api::class)

package com.albatech.casadei.ui.screens
import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import com.albatech.casadei.ui.atoms.AppButton
import com.albatech.casadei.ui.theme.LocalSpacing
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import com.albatech.casadei.core.network.NetworkClient
import com.albatech.casadei.api.auth.LoginRequestDto
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.edit
import com.albatech.casadei.core.auth.AuthPrefsKeys
import com.albatech.casadei.core.auth.authDataStore
@Composable
fun LoginScreen(
    onSuccess: () -> Unit
) {
    val space = LocalSpacing.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    val snackbar = remember { SnackbarHostState() }
    var show by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val emailError = email.isNotBlank() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val passError = password.isNotBlank() && password.length < 6
    val formValid = email.isNotBlank() && !emailError && password.isNotBlank() && !passError

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Login", style = MaterialTheme.typography.titleLarge) }) },
        snackbarHost = { SnackbarHost(snackbar) }
    ) { pad ->
        Column(
            modifier = Modifier
                .padding(pad)
                .padding(horizontal = space.lg, vertical = space.md)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(space.md)
        ) {
            Text("Welcome back 👋", style = MaterialTheme.typography.titleLarge)

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", style = MaterialTheme.typography.labelLarge) },
                isError = emailError,
                supportingText = { if (emailError) Text("Format email tidak valid") },
                singleLine = true,
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    errorBorderColor = MaterialTheme.colorScheme.error,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", style = MaterialTheme.typography.labelLarge) },
                isError = passError,
                supportingText = { if (passError) Text("Minimal 6 karakter") },
                singleLine = true,
                shape = MaterialTheme.shapes.medium,
                visualTransformation = if (show) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { show = !show }) {
                        Icon(
                            imageVector = if (show) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (show) "Sembunyikan password" else "Tampilkan password"
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    errorBorderColor = MaterialTheme.colorScheme.error,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(space.sm))

            AppButton(
                text = "Login",
                onClick = {
                    Log.d("Login", "email=$email, password=${"*".repeat(password.length)}")
                    loading = true
                    scope.launch {
                        loading = true
                        try {
                            // 🔥 Call API login di sini
                            val res = NetworkClient.authApi.login(
                                LoginRequestDto(
                                    email = email,
                                    password = password
                                )
                            )
                            val token = res.data.access_token
                            Log.d("Login", "Token: $token")
                            scope.launch {
                                context.authDataStore.edit { prefs ->
                                    prefs[AuthPrefsKeys.ACCESS_TOKEN] = token
                                }
                            }
                            // TODO: nanti simpan token ke DataStore di sini
                            snackbar.showSnackbar(
                                "Login success brow"
                            )
                            onSuccess()
                        } catch (e: Exception) {
                            Log.e("Login", "Login error", e)
                            snackbar.showSnackbar(
                                e.message ?: "Login gagal, coba lagi."
                            )
                        } finally {
                            loading = false
                        }
                    }


                    loading = false
                },
                loading = loading,
                enabled = formValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            )
        }
    }
}
