package com.murali.taskmanager.data.response

import java.io.Serializable

data class RequestDTO(
	val method: String? = null,
	val header: List<HeaderDTO?>? = null,
	val body: BodyDTO? = null,
	val url: UrlDTO? = null
)