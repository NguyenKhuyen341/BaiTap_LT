package com.example.el1.screens // Thay "el2" bằng tên project của bạn

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.el1.data.Book
import com.example.el1.data.Student
import com.example.el1.viewmodel.LibraryViewModel

// Màu đỏ cho checkbox (không đổi)
val checkboxRedColor = Color(0xFFC62828)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(viewModel: LibraryViewModel) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Quản lý", "DS Sách", "Sinh viên")

    // Lấy các trạng thái từ ViewModel
    val displayedStudent by viewModel.displayedStudent
    val studentNameInput by viewModel.studentNameInput

    Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = {
                            when (title) {
                                "Quản lý" -> Icon(Icons.Filled.Home, contentDescription = "Quản lý")
                                "DS Sách" -> Icon(Icons.Filled.MenuBook, contentDescription = "DS Sách")
                                "Sinh viên" -> Icon(Icons.Filled.AccountCircle, contentDescription = "Sinh viên")
                            }
                        },
                        label = { Text(title) }
                    )
                }
            }
        }
    ) { innerPadding ->
        if (selectedTab == 0) {
            ManagementContent(
                modifier = Modifier.padding(innerPadding),
                student = displayedStudent, // Truyền sinh viên (có thể null)
                studentNameInput = studentNameInput, // Truyền tên đang nhập
                onStudentNameChange = { newName ->
                    viewModel.studentNameInput.value = newName
                }, // Lambda để cập nhật tên
                onChangeClick = {
                    viewModel.searchStudentByName() // Nút "Thay đổi" gọi hàm search
                },
                onBookReturn = { book ->
                    viewModel.returnBook(book)
                }
            )
        } else {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                Text("Nội dung cho ${tabs[selectedTab]}")
            }
        }
    }
}

@Composable
fun ManagementContent(
    modifier: Modifier = Modifier,
    student: Student?, // Student có thể là null
    studentNameInput: String, // Tên trong ô input
    onStudentNameChange: (String) -> Unit, // Hàm cập nhật tên input
    onChangeClick: () -> Unit,
    onBookReturn: (Book) -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tiêu đề (không đổi)
        Text(
            text = "Hệ thống\nQuản lý Thư viện",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Phần Sinh viên (đã cập nhật)
        StudentInfoSection(
            studentName = studentNameInput,
            onNameChange = onStudentNameChange, // Truyền hàm onchange
            onChangeClick = onChangeClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Phần Danh sách sách (đã cập nhật)
        BookListSection(
            student = student, // Truyền cả object student (nullable)
            onBookReturn = onBookReturn
        )

        Spacer(modifier = Modifier.weight(1f))

        // Nút Thêm (không đổi)
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Thêm", fontSize = 18.sp)
        }
    }
}

@Composable
fun StudentInfoSection(
    studentName: String,
    onNameChange: (String) -> Unit, // Thêm
    onChangeClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Sinh viên",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = studentName, // Hiển thị tên từ state input
                onValueChange = onNameChange, // Cho phép người dùng nhập
                readOnly = false, // CHO PHÉP NHẬP
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp),
                label = { Text("Nhập tên sinh viên") }, // Thêm label
                singleLine = true // Đảm bảo nhập trên 1 dòng
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = onChangeClick, // Nút này giờ là "Tìm kiếm"
                modifier = Modifier.height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Thay đổi")
            }
        }
    }
}

@Composable
fun BookListSection(
    student: Student?, // Nhận Student (nullable)
    onBookReturn: (Book) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Danh sách sách",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 200.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF0F0F0) // Màu xám nhạt
            )
        ) {
            // *** LOGIC MỚI Ở ĐÂY ***
            when {
                // 1. Nếu không tìm thấy sinh viên (student là null)
                student == null -> {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(16.dp).defaultMinSize(minHeight = 200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Không tìm thấy sinh viên.\nVui lòng kiểm tra lại tên.",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }

                // 2. Nếu sinh viên không mượn sách ("Nguyễn Văn C")
                student.borrowedBooks.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(16.dp).defaultMinSize(minHeight = 200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Bạn chưa mượn quyển sách nào.\nNhấn 'Thêm' để bắt đầu hành trình đọc sách!",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }

                // 3. Nếu sinh viên có mượn sách ("Nguyễn Văn A")
                else -> {
                    LazyColumn(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        items(student.borrowedBooks) { book ->
                            BookItem(
                                book = book,
                                onCheckedChange = { onBookReturn(book) }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookItem(book: Book, onCheckedChange: (Boolean) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Checkbox(
                checked = true, // Sách trong danh sách mượn luôn được check
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = checkboxRedColor, // Màu đỏ
                    checkmarkColor = Color.White
                )
            )
            Text(
                text = book.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }
    }
}

// Hàm Preview (xem trước)
@Preview(showBackground = true, device = "id:pixel_6")
@Composable
fun LibraryScreenPreview() {
    @Suppress("ViewModelCreation")
    LibraryScreen(viewModel = LibraryViewModel())
}