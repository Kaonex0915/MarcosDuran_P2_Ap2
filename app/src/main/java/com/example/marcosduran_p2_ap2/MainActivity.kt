package com.example.marcosduran_p2_ap2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.marcosduran_p2_ap2.presentation.navigation.ContributorNavHost
import com.example.marcosduran_p2_ap2.ui.theme.MarcosDuran_P2_Ap2Theme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarcosDuran_P2_Ap2Theme {
                val navHost = rememberNavController()
                ContributorNavHost(navHost)
            }
        }
    }
}