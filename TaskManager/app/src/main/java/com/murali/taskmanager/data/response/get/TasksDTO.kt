package com.murali.taskmanager.data.response.get

import com.google.gson.annotations.SerializedName

data class TasksDTO(
    @SerializedName("task_id")
    val task_id: Int,
    @SerializedName("task_detail")
    val task_detail: CalendarTaskModel
)