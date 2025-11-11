// File: AppScreens.kt
package com.example.el3 // Thay bằng package của bạn

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage

// Định nghĩa các đường dẫn (route) cho các màn hình
object Routes {
    const val GET_STARTED = "get_started"
    const val UI_LIST = "ui_list"
    const val TEXT_DETAIL = "text_detail"
    const val IMAGES_DETAIL = "images_detail"
    const val TEXTFIELD_DETAIL = "textfield_detail"
    const val ROW_LAYOUT_DETAIL = "row_layout_detail"
    const val COLUMN_LAYOUT_DETAIL = "column_layout_detail" // <-- THÊM ROUTE MỚI
}

// Hàm Composable chính, quản lý việc điều hướng
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.GET_STARTED) {
        composable(Routes.GET_STARTED) { GetStartedScreen(navController) }
        composable(Routes.UI_LIST) { UIComponentsListScreen(navController) }
        composable(Routes.TEXT_DETAIL) { TextDetailScreen(navController) }
        composable(Routes.IMAGES_DETAIL) { ImagesScreen(navController) }
        composable(Routes.TEXTFIELD_DETAIL) { TextFieldScreen(navController) }
        composable(Routes.ROW_LAYOUT_DETAIL) { RowLayoutScreen(navController) }
        composable(Routes.COLUMN_LAYOUT_DETAIL) { ColumnLayoutScreen(navController) } // <-- THÊM MÀN HÌNH MỚI VÀO NAVHOST
    }
}


// --- Màn hình 1: Get Started ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetStartedScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("UI components", fontSize = 16.sp) })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text("Nguyễn Khuyến", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("051205000012", color = Color.Gray)

            Spacer(modifier = Modifier.weight(0.5f))

            Image(
                painter = painterResource(id = R.drawable.jetpack_compose_logo),
                contentDescription = "Jetpack Compose Logo",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text("Jetpack Compose", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Jetpack Compose is a modern UI toolkit for building native Android applications using a declarative programming approach.",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { navController.navigate(Routes.UI_LIST) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF))
            ) {
                Text("I'm ready", modifier = Modifier.padding(vertical = 8.dp), fontSize = 18.sp, color = Color.White)
            }
        }
    }
}


// --- Màn hình 2: Danh sách các thành phần UI ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UIComponentsListScreen(navController: NavController) {
    Scaffold(topBar = { TopAppBar(title = { Text("UI Components List") }) }) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { SectionHeader("Display") }
            item { ComponentListItem("Text", "Displays text", onClick = { navController.navigate(Routes.TEXT_DETAIL) }) }
            item { ComponentListItem("Image", "Displays an image", onClick = { navController.navigate(Routes.IMAGES_DETAIL) }) }

            item { SectionHeader("Input") }
            item { ComponentListItem("TextField", "Input field for text", onClick = { navController.navigate(Routes.TEXTFIELD_DETAIL) }) }
            item { ComponentListItem("PasswordField", "Input field for passwords", onClick = {}) }

            item { SectionHeader("Layout") }
            // CẬP NHẬT ONCLICK Ở ĐÂY
            item { ComponentListItem("Column", "Arranges elements vertically", onClick = { navController.navigate(Routes.COLUMN_LAYOUT_DETAIL) }) }
            item { ComponentListItem("Row", "Arranges elements horizontally", onClick = { navController.navigate(Routes.ROW_LAYOUT_DETAIL) }) }
            item { ComponentListItem("Tìm hiểu", "Tìm ra tất cả các thành phần UI cơ bản", containerColor = Color(0xFFFFCDD2), onClick = {}) }
        }
    }
}


// --- Màn hình Column Layout (MÀN HÌNH MỚI) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnLayoutScreen(navController: NavController) {
    Scaffold(
        topBar = { SimpleTopAppBar("Column Layout", navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center, // Căn giữa theo chiều dọc
            horizontalAlignment = Alignment.CenterHorizontally // Căn giữa theo chiều ngang
        ) {
            val lightGreen = Color(0xFFC8E6C9)
            val darkGreen = Color(0xFF81C784)

            // Box 1
            Box(
                modifier = Modifier
                    .size(width = 280.dp, height = 90.dp)
                    .background(lightGreen, RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.height(24.dp))
            // Box 2
            Box(
                modifier = Modifier
                    .size(width = 280.dp, height = 90.dp)
                    .background(darkGreen, RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.height(24.dp))
            // Box 3
            Box(
                modifier = Modifier
                    .size(width = 280.dp, height = 90.dp)
                    .background(lightGreen, RoundedCornerShape(16.dp))
            )
        }
    }
}

// --- Các màn hình chi tiết khác (không thay đổi) ---

// Màn hình 3: Chi tiết Text
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextDetailScreen(navController: NavController) {
    Scaffold(topBar = { SimpleTopAppBar("Text Detail", navController) }) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            val annotatedString = buildAnnotatedString {
                append("The quick ")
                withStyle(style = SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                    append("quick")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold ,color = Color( 0xFF7955548), fontSize = 30.sp)) { append("Brown") }
                append(" fox jumps ")
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) { append("over") }
                append(" the lazy dog.")
            }
            Text(text = annotatedString, fontSize = 28.sp, lineHeight = 40.sp)
        }
    }
}

// Màn hình 4: Chi tiết Imagez
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagesScreen(navController: NavController) {
    Scaffold(topBar = { SimpleTopAppBar("Images", navController) }) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = "https://phongdaotao2.uth.edu.vn/wp-content/uploads/2023/01/Loop-BTY.png",
                contentDescription = "UTH University Building",
                modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Text("https://phongdaotao2.uth.edu.vn/...", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.uth_logo),
                contentDescription = "In app image",
                modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Text("In app")
        }
    }
}

// Màn hình 5: Chi tiết TextField
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldScreen(navController: NavController) {
    var textValue by remember { mutableStateOf("") }
    Scaffold(topBar = { SimpleTopAppBar("TextField", navController) }) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = textValue,
                onValueChange = { textValue = it },
                label = { Text("Thông tin nhập") },
                modifier = Modifier.fillMaxWidth()
            )
            Text(text = "Tự động cập nhật dữ liệu theo textfield: $textValue")
        }
    }
}

// Màn hình 6: Chi tiết Row Layout
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowLayoutScreen(navController: NavController) {
    Scaffold(topBar = { SimpleTopAppBar("Row Layout", navController) }) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            repeat(4) { rowIndex ->
                LayoutRow(highlightedIndex = if (rowIndex == 2) 1 else -1, text = if(rowIndex == 2 && 1 == 1) "MFS06MT" else "")
            }
        }
    }
}
@Composable
fun LayoutRow(highlightedIndex: Int = -1, text: String = "") {
    val lightBlue = Color(0xFFD0E6FF)
    val darkBlue = Color(0xFF4A90E2)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        (0..2).forEach { colIndex ->
            val color = if (colIndex == highlightedIndex) darkBlue else lightBlue
            Box(
                modifier = Modifier
                    .size(width = 90.dp, height = 60.dp)
                    .background(color, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (text.isNotEmpty() && colIndex == highlightedIndex) {
                    Text(text, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}


// --- Các thành phần phụ trợ (không thay đổi) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(title: String, navController: NavController) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    )
}

@Composable
fun ComponentListItem(title: String, description: String, containerColor: Color = MaterialTheme.colorScheme.primaryContainer, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold)
            Text(text = description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}