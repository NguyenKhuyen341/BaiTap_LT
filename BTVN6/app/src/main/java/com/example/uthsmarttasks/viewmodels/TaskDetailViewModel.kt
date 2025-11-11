package com.example.uthsmarttasks.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uthsmarttasks.data.ApiService
import com.example.uthsmarttasks.data.RetrofitInstance
import com.example.uthsmarttasks.data.TaskDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Định nghĩa các trạng thái cho màn hình Detail
sealed interface TaskDetailUiState {
    data class Success(val task: TaskDetail) : TaskDetailUiState
    object Loading : TaskDetailUiState
    data class Error(val message: String) : TaskDetailUiState
    object DeleteSuccess : TaskDetailUiState // Trạng thái khi xóa thành công
}

class TaskDetailViewModel(
    savedStateHandle: SavedStateHandle // Dùng để lấy 'taskId' từ navigation
) : ViewModel() {

    private val apiService: ApiService = RetrofitInstance.api

    // Lấy 'taskId' từ arguments của navigation
    private val taskId: String = checkNotNull(savedStateHandle["taskId"])

    private val _uiState = MutableStateFlow<TaskDetailUiState>(TaskDetailUiState.Loading)
    val uiState: StateFlow<TaskDetailUiState> = _uiState.asStateFlow()

    init {
        fetchTaskDetail() // Gọi API chi tiết ngay khi có taskId
    }

    private fun fetchTaskDetail() {
        _uiState.value = TaskDetailUiState.Loading
        viewModelScope.launch {
            try {
                val taskDetail = apiService.getTaskDetail(taskId)
                _uiState.value = TaskDetailUiState.Success(taskDetail)
            } catch (e: Exception) {
                _uiState.value = TaskDetailUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun deleteTask() {
        viewModelScope.launch {
            try {
                val response = apiService.deleteTask(taskId)
                if (response.isSuccessful) {
                    _uiState.value = TaskDetailUiState.DeleteSuccess
                } else {
                    _uiState.value = TaskDetailUiState.Error("Failed to delete task")
                }
            } catch (e: Exception) {
                _uiState.value = TaskDetailUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}