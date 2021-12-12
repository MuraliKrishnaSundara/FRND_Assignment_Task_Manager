package com.murali.taskmanager.data.response.post

import com.google.gson.annotations.SerializedName

data class PostTasksResponseDTO(
	@SerializedName("status")
	val status: String
)