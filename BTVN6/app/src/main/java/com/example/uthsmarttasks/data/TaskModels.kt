package com.example.uthsmarttasks.data

import com.google.gson.annotations.SerializedName

// Model cho item trong danh sách (List)
data class TaskSummary(
    val id: String,
    val title: String,
    val status: String,
    @SerializedName("due_date") // Key trong JSON là 'due_date'
    val dueDate: String
)

// Model cho màn hình Chi tiết (Detail)
data class TaskDetail(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val status: String,
    val priority: String,
    // Giả sử API trả về nốt 2 list này
    val subtasks: List<Subtask> = emptyList(),
    val attachments: List<Attachment> = emptyList()
)

data class Subtask(
    val id: String,
    val text: String,
    @SerializedName("is_completed")
    val isCompleted: Boolean
)

data class Attachment(
    val id: String,
    val filename: String,
    val url: String
)