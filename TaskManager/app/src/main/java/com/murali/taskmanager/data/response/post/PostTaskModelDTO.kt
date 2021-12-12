package com.murali.taskmanager.data.response.post

import com.google.gson.annotations.SerializedName

//data class for posting data to api
data class PostTaskModelDTO(

    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("date")
    val date: String

)