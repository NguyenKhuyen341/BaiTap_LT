package com.example.uthsmarttasks // <-- Đảm bảo đúng package

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.uthsmarttasks.ui.AppNavigation
import com.example.uthsmarttasks.ui.theme.UTHSmartTasksTheme // <-- Đổi tên này nếu theme của bạn khác

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UTHSmartTasksTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Khởi chạy bộ điều hướng
                    AppNavigation()
                }
            }
        }
    }
}