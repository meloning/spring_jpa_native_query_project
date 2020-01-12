package com.example.spring_jpa_native_query_project.core.repository

import com.example.spring_jpa_native_query_project.core.model.Bread

interface BreadRepository {
    fun findById(id: Long): Bread?
    fun findByFormworkId(formworkId: Long): List<Bread>
    fun updateByFormworkId(bread: Bread, breadIds: List<Long>)
    fun save(bread: Bread): Bread
}