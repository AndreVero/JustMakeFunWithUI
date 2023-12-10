package com.vero.justmakefunwithui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vero.justmakefunwithui.components.baseclock.Clock
import com.vero.justmakefunwithui.ui.theme.JustMakeFunWithUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JustMakeFunWithUITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Clock(radius = 150.dp, modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}
