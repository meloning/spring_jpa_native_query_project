package com.example.spring_jpa_native_query_project.boundary

import com.example.spring_jpa_native_query_project.core.model.Bread
import com.example.spring_jpa_native_query_project.core.model.Formwork
import com.example.spring_jpa_native_query_project.core.model.FormworkType
import com.example.spring_jpa_native_query_project.core.repository.BreadRepository
import com.example.spring_jpa_native_query_project.core.repository.FormworkRepository
import com.example.spring_jpa_native_query_project.core.usecase.CreateBreadFromFormworkUsecase
import com.example.spring_jpa_native_query_project.core.usecase.UpdateBreadFromFormworkUsecase
import javassist.NotFoundException
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.lang.Exception

@Component
class SpringJpaRunner(
    private val formworkRepository: FormworkRepository,
    private val breadRepository: BreadRepository,
    private val updateBreadFromFormworkUsecase: UpdateBreadFromFormworkUsecase,
    private val createBreadFromFormworkUsecase: CreateBreadFromFormworkUsecase
) : ApplicationRunner {

    private fun generateFormwork(): Formwork {
        val newFormwork = Formwork(
                title = "test",
                description = "test formwork",
                formworkType = FormworkType.CANNON,
                properties = setOf("ice"),
                polygonIds = listOf(1L, 2L, 3L)
        )
        return this.formworkRepository.save(newFormwork)
    }

    override fun run(args: ApplicationArguments?) {
//        println(this.generateFormwork())
//        println(this.createBreadFromFormworkUsecase.execute(1))
        println(updateBreadFromFormworkUsecase.execute(1))
    }
}