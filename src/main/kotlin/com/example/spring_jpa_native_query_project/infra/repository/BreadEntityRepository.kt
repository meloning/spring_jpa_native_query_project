package com.example.spring_jpa_native_query_project.infra.repository

import com.example.spring_jpa_native_query_project.core.model.Bread
import com.example.spring_jpa_native_query_project.core.repository.BreadRepository
import com.example.spring_jpa_native_query_project.infra.entity.BreadEntity
import com.example.spring_jpa_native_query_project.infra.entity.toBread
import com.example.spring_jpa_native_query_project.infra.entity.toBreadEntity
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

interface BreadEntityRepository : CrudRepository<BreadEntity, Long> {

    fun findByFormworkId(formworkId: Long): List<BreadEntity>

//    @Transactional
    @Modifying
    @Query("""
        update BreadEntity b
        set 
            b.title = :#{#breadEntity.title},
            b.description = :#{#breadEntity.description},
            b.type = :#{#breadEntity.type},
            b.polygonIds = :#{#breadEntity.polygonIds}
        where
            b.formworkId = :#{#breadEntity.formworkId}
    """)
    fun updateByFormworkId(@Param("breadEntity") breadEntity: BreadEntity): Int

}

@Repository
class BreadRepositoryImpl(
        private val breadEntityRepository: BreadEntityRepository
) : BreadRepository {
    override fun save(bread: Bread): Bread {
        return this.breadEntityRepository.save(bread.toBreadEntity()).toBread()
    }

    override fun findById(id: Long): Bread? {
        return this.breadEntityRepository.findByIdOrNull(id)?.toBread()
    }

    override fun updateByFormworkId(bread: Bread, breadIds: List<Long>) {
        if (bread.properties.isNotEmpty()) {
            val breads = breadIds.map { bread.copy(id = it).toBreadEntity() }
            this.breadEntityRepository.saveAll(breads)
        } else {
            this.breadEntityRepository.updateByFormworkId(bread.toBreadEntity())
        }
    }

    override fun findByFormworkId(formworkId: Long): List<Bread> {
        return this.breadEntityRepository.findByFormworkId(formworkId).map { it.toBread() }
    }
}