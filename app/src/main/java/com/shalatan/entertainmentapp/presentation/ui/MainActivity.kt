package com.shalatan.entertainmentapp.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.shalatan.entertainmentapp.navigation.AppNavigation
import com.shalatan.entertainmentapp.presentation.ui.theme.MoviesHexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesHexTheme {
                AppNavigation()
            }
        }
    }
}