package com.example.el1 // Thay "el2" bằng tên project của bạn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels // Import để dùng by viewModels()
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.el1.screens.LibraryScreen // Import màn hình của bạn
import com.example.el1.ui.theme.EL1Theme // Import Theme của bạn
import com.example.el1.viewmodel.LibraryViewModel // Import ViewModel của bạn

class MainActivity : ComponentActivity() {

    // Khởi tạo ViewModel
    private val viewModel: LibraryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EL1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Gọi màn hình chính và truyền ViewModel vào
                    LibraryScreen(viewModel = viewModel)
                }
            }
        }
    }
}