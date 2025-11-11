package com.example.el2 // Package của project mới

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.el2.auth.ForgotPasswordFlow // Import luồng mới
import com.example.el2.ui.theme.EL2Theme // Import Theme mới

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Giữ nguyên từ template mới của bạn
        setContent {
            EL2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ForgotPasswordFlow() // Gọi luồng "Quên mật khẩu" ở đây
                }
            }
        }
    }
}