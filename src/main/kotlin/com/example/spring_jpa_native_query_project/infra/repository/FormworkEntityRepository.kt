package com.example.spring_jpa_native_query_project.infra.repository

import com.example.spring_jpa_native_query_project.core.model.Formwork
import com.example.spring_jpa_native_query_project.core.repository.FormworkRepository
import com.example.spring_jpa_native_query_project.infra.entity.FormworkEntity
import com.example.spring_jpa_native_query_project.infra.entity.toFormwork
import com.example.spring_jpa_native_query_project.infra.entity.toFormworkEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

interface FormworkEntityRepository : CrudRepository<FormworkEntity, Long> {

}

@Repository
class FormworkRepositoryImpl(
        private val formworkEntityRepository: FormworkEntityRepository
) : FormworkRepository {
    override fun findById(id: Long): Formwork? {
        return this.formworkEntityRepository.findByIdOrNull(id)?.toFormwork()
    }

    override fun save(formwork: Formwork): Formwork {
        return this.formworkEntityRepository.save(formwork.toFormworkEntity()).toFormwork()
    }
}