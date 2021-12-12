package com.murali.taskmanager.data.response.get

import com.google.gson.annotations.SerializedName

data class GetTasksResponseDTO(

    @SerializedName("tasks")
    val tasks: List<GetTaskIdAndTaskModelDTO>

)