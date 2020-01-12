package com.example.spring_jpa_native_query_project.infra.converter

import com.example.spring_jpa_native_query_project.core.model.FormworkType
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class FormworkConverter : AttributeConverter<FormworkType, Int> {
    override fun convertToDatabaseColumn(attribute: FormworkType?): Int? {
        return attribute?.type
    }

    override fun convertToEntityAttribute(dbData: Int?): FormworkType? {
        return dbData?.let {
            FormworkType.fromType(it)
        }
    }
}