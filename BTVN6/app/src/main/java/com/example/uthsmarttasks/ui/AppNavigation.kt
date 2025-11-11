package com.example.uthsmarttasks.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uthsmarttasks.viewmodels.TaskDetailViewModel
import com.example.uthsmarttasks.viewmodels.TaskListViewModel

// Định nghĩa các "đường dẫn" (route)
object AppRoutes {
    const val TASK_LIST = "taskList"
    const val TASK_DETAIL = "taskDetail/{taskId}" // {taskId} là tham số
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.TASK_LIST // Bắt đầu ở màn hình List
    ) {
        // Màn hình Danh sách công việc
        composable(route = AppRoutes.TASK_LIST) {
            val viewModel: TaskListViewModel = viewModel()
            TaskListScreen(
                viewModel = viewModel,
                onTaskClick = { taskId ->
                    // Khi nhấn, điều hướng sang màn hình Detail
                    navController.navigate("taskDetail/$taskId")
                }
            )
        }

        // Màn hình Chi tiết công việc
        composable(
            route = AppRoutes.TASK_DETAIL,
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) {
            val viewModel: TaskDetailViewModel = viewModel()
            TaskDetailScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack() // Quay lại
                },
                onTaskDeleted = {
                    navController.popBackStack() // Xóa xong cũng quay lại
                }
            )
        }
    }
}