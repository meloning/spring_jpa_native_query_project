package com.example.spring_jpa_native_query_project.infra.entity


import com.example.spring_jpa_native_query_project.core.model.Formwork
import com.example.spring_jpa_native_query_project.core.model.FormworkType
import com.example.spring_jpa_native_query_project.infra.converter.FormworkConverter
import org.hibernate.annotations.IndexColumn
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.time.ZoneId
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@EntityListeners(AuditingEntityListener::class)
class FormworkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id: Long? = null

    @Column
    @NotNull
    var title: String = ""

    @Column(length = 65535, columnDefinition = "TEXT")
    var description: String? = null

    @Column
    @NotNull
    @Convert(converter = FormworkConverter::class)
    var formworkType: FormworkType = FormworkType.CANNON

    @ElementCollection(targetClass = String::class)
    @CollectionTable(
            name = "formwork_properties",
            joinColumns = [(JoinColumn(name = "formwork_id"))]
    )
    @OrderColumn(name = "index_id")
    var properties: List<String> = emptyList() // It is List type If ordercolumn accept
    // reference: https://stackoverflow.com/a/28740520/12591891

    @Column
    @NotNull
    var polygonIds: String = ""

    // association
    @OneToMany(mappedBy = "formwork", fetch = FetchType.LAZY)
    val breads: Collection<BreadEntity> = emptySet()

    @CreatedDate
    @Column(updatable = false)
    var createdAt: Instant? = null

    @LastModifiedDate
    @Column(updatable = true)
    var updatedAt: Instant? = null
}

fun Formwork.toFormworkEntity(): FormworkEntity {
    val formworkEntity = FormworkEntity()
    formworkEntity.id = this.id
    formworkEntity.title = this.title
    formworkEntity.description = this.description
    formworkEntity.formworkType = this.formworkType
    formworkEntity.properties = this.properties.toList()
    formworkEntity.polygonIds = this.polygonIds.joinToString(",")
    return formworkEntity
}

fun FormworkEntity.toFormwork() =
    Formwork(
            id = this.id,
            title = this.title,
            description = this.description,
            formworkType = this.formworkType,
            properties = this.properties,
            polygonIds = this.polygonIds.split(",").filter { it.isNotBlank() }.map { it.toLong() },
            createdAt = this.createdAt?.atZone(ZoneId.systemDefault()),
            updatedAt = this.updatedAt?.atZone(ZoneId.systemDefault())

    )

