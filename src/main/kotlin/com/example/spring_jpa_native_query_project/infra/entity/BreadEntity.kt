package com.example.spring_jpa_native_query_project.infra.entity

import com.example.spring_jpa_native_query_project.core.model.Bread
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.time.ZoneId
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@EntityListeners(AuditingEntityListener::class)
class BreadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id: Long? = null

    @Column
    @NotNull
    var title: String = ""

    @Column(length = 65535, columnDefinition = "TEXT")
    var description: String? = null

    @Column(updatable = false)
    @NotNull
    var type: String = "Cannon"

    @ElementCollection(targetClass = String::class)
    @CollectionTable(name = "bread_properties", joinColumns = [(JoinColumn(name = "bread_id"))])
    @OrderColumn
    var properties: Collection<String> = emptySet()

    @Column
    @NotNull
    var polygonIds: String = ""

    // association
    @ManyToOne
    @JoinColumn(name = "formwork_id")
    var formwork: FormworkEntity? = null

    @Column(name = "formwork_id", updatable = false, insertable = false)
    var formworkId: Long? = null

    @CreatedDate
    @Column(updatable = false)
    var createdAt: Instant? = null

    @LastModifiedDate
    @Column(updatable = true)
    var updatedAt: Instant? = null
}

fun Bread.toBreadEntity(): BreadEntity {
    val breadEntity = BreadEntity()
    breadEntity.id = this.id
    breadEntity.title = this.title
    breadEntity.description = this.description
    breadEntity.type = this.type
    breadEntity.properties = this.properties.toSet()
    breadEntity.polygonIds = this.polygonIds.joinToString(",")
    breadEntity.formwork = this.formwork?.toFormworkEntity()
    breadEntity.formworkId = this.formworkId
    return breadEntity
}

fun BreadEntity.toBread() =
    Bread(
            id = this.id,
            title = this.title,
            description = this.description,
            type = this.type,
            properties = this.properties,
            polygonIds = this.polygonIds.split(",").filter { it.isNotBlank() }.map { it.toLong() },
            formwork = this.formwork?.toFormwork(),
            formworkId = this.formworkId,
            createdAt = this.createdAt?.atZone(ZoneId.systemDefault()),
            updatedAt = this.updatedAt?.atZone(ZoneId.systemDefault())
    )