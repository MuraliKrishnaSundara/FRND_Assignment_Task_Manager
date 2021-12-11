package com.murali.taskmanager.data.response

import java.io.Serializable

data class UrlDTO(
	val raw: String? = null,
	val protocol: String? = null,
	val host: List<String?>? = null,
	val port: String? = null,
	val path: List<String?>? = null
)