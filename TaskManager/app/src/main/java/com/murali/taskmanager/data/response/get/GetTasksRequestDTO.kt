package com.murali.taskmanager.data.response.get

import com.google.gson.annotations.SerializedName

data class GetTasksRequestDTO(

    @SerializedName("user_id")
    val user_id: Int

)