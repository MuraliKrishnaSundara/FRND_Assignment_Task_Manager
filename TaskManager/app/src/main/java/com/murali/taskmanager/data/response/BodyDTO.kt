package com.murali.taskmanager.data.response

import java.io.Serializable

data class BodyDTO(
	val mode: String? = null,
	val raw: String? = null,
	val options: OptionsDTO? = null
)