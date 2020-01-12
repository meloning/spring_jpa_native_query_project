package com.example.spring_jpa_native_query_project.core.repository

import com.example.spring_jpa_native_query_project.core.model.Formwork

interface FormworkRepository {
    fun findById(id: Long): Formwork?
    fun save(formwork: Formwork): Formwork
}