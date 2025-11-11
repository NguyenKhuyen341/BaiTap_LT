package com.example.uthsmarttasks.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uthsmarttasks.data.ApiService
import com.example.uthsmarttasks.data.RetrofitInstance
import com.example.uthsmarttasks.data.TaskSummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Định nghĩa các trạng thái cho màn hình List
sealed interface TaskListUiState {
    data class Success(val tasks: List<TaskSummary>) : TaskListUiState
    object Empty : TaskListUiState // Trạng thái khi API trả về list rỗng
    object Loading : TaskListUiState
    data class Error(val message: String) : TaskListUiState
}

class TaskListViewModel : ViewModel() {

    private val apiService: ApiService = RetrofitInstance.api

    private val _uiState = MutableStateFlow<TaskListUiState>(TaskListUiState.Loading)
    val uiState: StateFlow<TaskListUiState> = _uiState.asStateFlow()

    init {
        fetchTasks() // Gọi API ngay khi ViewModel được tạo
    }

    fun fetchTasks() {
        _uiState.value = TaskListUiState.Loading
        viewModelScope.launch {
            try {
                val tasks = apiService.getTasks()
                if (tasks.isEmpty()) {
                    _uiState.value = TaskListUiState.Empty
                } else {
                    _uiState.value = TaskListUiState.Success(tasks)
                }
            } catch (e: Exception) {
                _uiState.value = TaskListUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}