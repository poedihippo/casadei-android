package com.albatech.casadei.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Base palette kamu (brand)
val BrandPrimary = Color(0xFF2563EB)   // biru-600
val BrandOnPrimary = Color(0xFFFFFFFF)
val BrandSecondary = Color(0xFF0EA5E9) // sky-500
val BrandTertiary = Color(0xFF10B981)  // emerald-500
val Error = Color(0xFFDC2626)

val LightColors = lightColorScheme(
    primary = BrandPrimary,
    onPrimary = BrandOnPrimary,
    secondary = BrandSecondary,
    tertiary = BrandTertiary,
    error = Error
    // sisanya biarin default, atau isi kalau mau presisi
)

val DarkColors = darkColorScheme(
    primary = BrandPrimary,
    onPrimary = BrandOnPrimary,
    secondary = BrandSecondary,
    tertiary = BrandTertiary,
    error = Error
)
