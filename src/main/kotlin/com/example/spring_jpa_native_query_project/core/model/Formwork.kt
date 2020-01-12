package com.example.spring_jpa_native_query_project.core.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import java.time.ZonedDateTime

enum class FormworkType(@get:JsonValue val type: Int) {
    CANNON(0),
    BULLET(1);

    companion object {
        private val map = FormworkType.values().associateBy(FormworkType::type)
        @JsonCreator
        @JvmStatic
        fun fromType(type: Int) = map.getOrDefault(type, CANNON)
    }
}

data class Formwork(
    val id: Long? = null,
    val title: String,
    val description: String? = null,
    val formworkType: FormworkType,
    val properties: Collection<String>,
    val polygonIds: Iterable<Long>,
    val createdAt: ZonedDateTime? = null,
    val updatedAt: ZonedDateTime? = null
) {
    fun generateBread(): Bread {
        return Bread(
            title = this.title,
            description = this.description,
            type = this.formworkType.name,
            properties = this.properties.toList(),
            polygonIds = this.polygonIds.toList(),
            formwork = this
        )
    }
}