package com.example.spring_jpa_native_query_project.core.usecase

import com.example.spring_jpa_native_query_project.core.model.Bread
import com.example.spring_jpa_native_query_project.core.model.Formwork
import com.example.spring_jpa_native_query_project.core.model.FormworkType
import com.example.spring_jpa_native_query_project.core.repository.BreadRepository
import com.example.spring_jpa_native_query_project.core.repository.FormworkRepository
import org.springframework.dao.InvalidDataAccessApiUsageException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import kotlin.Exception

@Component
class UpdateBreadFromFormworkUsecase(
        private val formworkRepository: FormworkRepository,
        private val breadRepository: BreadRepository
) {

    @Transactional
    fun execute(formworkId: Long): Formwork {
        val formwork = this.formworkRepository.findById(formworkId) ?: throw Exception("Not found Formwork.id")
        val temp = formwork.copy(
                title = "updated title2",
                description = "updated description2",
                formworkType = FormworkType.BULLET,
                polygonIds = listOf(1L, 2L),
                properties = setOf("fire")
        )
        val updateFormwork = this.formworkRepository.save(temp)
//        val breads = this.breadRepository.findByFormworkId(updateFormwork.id ?: 0).map { it.id ?: 0 }
//        this.breadRepository.updateByFormworkId(
//                Bread(
//                        title = updateFormwork.title,
//                        description = updateFormwork.description,
//                        type = updateFormwork.formworkType.name,
//                        properties = if (temp.properties.isNotEmpty()) {
//                            updateFormwork.properties
//                        } else {
//                            emptySet()
//                        },
//                        polygonIds = updateFormwork.polygonIds
//                ), breads
//        )
        return updateFormwork
    }
}