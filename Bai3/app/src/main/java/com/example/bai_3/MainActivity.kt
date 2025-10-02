package com.example.bai_3

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bai_3.ui.theme.Bai_3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Bai_3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AgeCheckerScreen()
                }
            }
        }
    }
}

@Composable
fun AgeCheckerScreen() {
    var nameInput by remember { mutableStateOf("") }
    var ageInput by remember { mutableStateOf("") }
    var resultMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "THỰC HÀNH 01",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(48.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Họ và tên", modifier = Modifier.width(80.dp))
            OutlinedTextField(
                value = nameInput,
                onValueChange = { nameInput = it },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Tuổi", modifier = Modifier.width(80.dp))
            OutlinedTextField(
                value = ageInput,
                onValueChange = { ageInput = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val age = ageInput.toIntOrNull()
                val name = nameInput.trim()

                if (name.isBlank() || age == null) {
                    resultMessage = "Vui lòng nhập đầy đủ họ tên và tuổi hợp lệ."
                } else {
                    val ageCategory = when {
                        age > 65 -> "Người già"
                        age in 7..65 -> "Người lớn"
                        age in 2..6 -> "Trẻ em"
                        else -> "Em bé"
                    }
                    resultMessage = "Chào $name, bạn là $ageCategory."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Kiểm tra")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = resultMessage,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AgeCheckerScreenPreview() {
    Bai_3Theme {
        AgeCheckerScreen()
    }
}