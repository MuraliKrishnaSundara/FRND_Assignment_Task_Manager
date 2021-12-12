package com.murali.taskmanager.data.response.delete

import com.google.gson.annotations.SerializedName

data class DeleteTaskRequestDTO(

    @SerializedName("user_id")
    val user_id: Int,

    @SerializedName("task_id")
    val task_id: Int

)