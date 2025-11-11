package com.example.bai_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bai_2.ui.theme.Bai_2Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Bai_2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EmailValidatorScreen()
                }
            }
        }
    }
}

@Composable
fun EmailValidatorScreen() {
    var emailInput by remember { mutableStateOf("") }
    var validationMessage by remember { mutableStateOf("") }
    var messageColor by remember { mutableStateOf(Color.Red) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Tiêu đề
        Text(
            text = "Thực hành 02",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Ô nhập liệu Email
        OutlinedTextField(
            value = emailInput,
            onValueChange = {
                emailInput = it
                validationMessage = ""
            },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = validationMessage,
            color = messageColor,
            modifier = Modifier
                .padding(top = 8.dp)
                .height(20.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (emailInput.isBlank()) {
                    validationMessage = "Email không hợp lệ"
                    messageColor = Color.Red
                } else if (!emailInput.contains("@")) {
                    validationMessage = "Email không đúng định dạng"
                    messageColor = Color.Red
                } else {
                    validationMessage = "Bạn đã nhập email hợp lệ"
                    messageColor = Color(0xFF006400) 
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Kiểm tra")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EmailValidatorScreenPreview() {
    Bai_2Theme {
        EmailValidatorScreen()
    }
}