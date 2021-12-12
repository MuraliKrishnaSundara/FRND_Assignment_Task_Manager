package com.murali.taskmanager.data.response.post

import com.google.gson.annotations.SerializedName

data class PostTasksRequestDTO(
    @SerializedName("user_id")
    val user_id: Int,
    @SerializedName("task")
    val task: TaskDTO
)