package com.example.spring_jpa_native_query_project.core.model

import java.time.ZonedDateTime

data class Bread(
        val id: Long? = null,
        val title: String,
        val description: String? = null,
        val type: String,
        val properties: Collection<String>,
        val polygonIds: Iterable<Long>,
        val createdAt: ZonedDateTime? = null,
        val updatedAt: ZonedDateTime? = null,
        // association
        val formwork: Formwork? = null,
        val formworkId: Long? = null
)