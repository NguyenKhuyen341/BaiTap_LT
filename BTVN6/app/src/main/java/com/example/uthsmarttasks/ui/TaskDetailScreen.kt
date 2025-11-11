package com.example.uthsmarttasks.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uthsmarttasks.data.TaskDetail
import com.example.uthsmarttasks.viewmodels.TaskDetailUiState
import com.example.uthsmarttasks.viewmodels.TaskDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    viewModel: TaskDetailViewModel,
    onNavigateBack: () -> Unit,
    onTaskDeleted: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // Biến state để kiểm soát hộp thoại (dialog) xác nhận xóa
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Theo dõi trạng thái DeleteSuccess
    LaunchedEffect(uiState) {
        if (uiState is TaskDetailUiState.DeleteSuccess) {
            onTaskDeleted()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    // Nút Xóa (mở dialog)
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.Red
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is TaskDetailUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is TaskDetailUiState.Error -> {
                    Text(
                        text = "Lỗi: ${state.message}",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is TaskDetailUiState.Success -> {
                    // Hiển thị nội dung chi tiết
                    TaskDetailContent(task = state.task)
                }
                is TaskDetailUiState.DeleteSuccess -> {
                    // Đã xử lý bằng LaunchedEffect
                }
            }
        }
    }

    // Hộp thoại xác nhận xóa
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Xác nhận xóa") },
            text = { Text("Bạn có chắc chắn muốn xóa công việc này?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteTask()
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Xóa")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialog = false }) {
                    Text("Hủy")
                }
            }
        )
    }
}

// Composable chứa nội dung chi tiết
@Composable
fun TaskDetailContent(task: TaskDetail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // Cho phép cuộn
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = task.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = task.description,
            fontSize = 16.sp,
            color = Color.Gray
        )
        // Hiển thị các thông tin khác (Status, Priority...)
        InfoRow("Category", task.category)
        InfoRow("Status", task.status)
        InfoRow("Priority", task.priority)

        // Bạn có thể thêm LazyColumn cho Subtasks và Attachments
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "$label: ",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.DarkGray
        )
    }
}