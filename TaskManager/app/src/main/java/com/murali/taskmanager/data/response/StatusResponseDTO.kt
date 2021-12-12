package com.murali.taskmanager.data.response

import com.google.gson.annotations.SerializedName

data class StatusResponseDTO(
	@SerializedName("status")
	val status: String
)