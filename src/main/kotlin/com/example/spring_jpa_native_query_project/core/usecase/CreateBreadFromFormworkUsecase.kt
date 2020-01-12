package com.example.spring_jpa_native_query_project.core.usecase

import com.example.spring_jpa_native_query_project.core.model.Bread
import com.example.spring_jpa_native_query_project.core.repository.BreadRepository
import com.example.spring_jpa_native_query_project.core.repository.FormworkRepository
import javassist.NotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CreateBreadFromFormworkUsecase(
        private val formworkRepository: FormworkRepository,
        private val breadRepository: BreadRepository
) {

    @Transactional
    fun execute(formworkId: Long): Bread {
        val formwork = this.formworkRepository.findById(formworkId) ?: throw NotFoundException("Not found Formwork.id")
        return this.breadRepository.save(formwork.generateBread())
    }
}