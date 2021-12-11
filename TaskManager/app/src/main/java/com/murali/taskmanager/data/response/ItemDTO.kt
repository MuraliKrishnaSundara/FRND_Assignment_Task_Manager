package com.murali.taskmanager.data.response

import java.io.Serializable

data class ItemDTO(
	val name: String? = null,
	val request: RequestDTO? = null,
	val response: List<Any?>? = null
)