package com.murali.taskmanager.data.response

import java.io.Serializable

data class TaskResponseDTO(
	val info: InfoDTO? = null,
	val item: List<ItemDTO?>? = null
)