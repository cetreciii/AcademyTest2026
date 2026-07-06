package com.example.academytest2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.academytest2026.ui.ContentScreen
import com.example.academytest2026.ui.theme.AcademyTest2026Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AcademyTest2026Theme {
                ContentScreen()
            }
        }
    }
}