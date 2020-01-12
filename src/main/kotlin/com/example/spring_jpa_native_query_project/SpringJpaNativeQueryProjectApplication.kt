package com.example.spring_jpa_native_query_project

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableJpaAuditing
@EnableTransactionManagement
class SpringJpaNativeQueryProjectApplication

fun main(args: Array<String>) {
    runApplication<SpringJpaNativeQueryProjectApplication>(*args)
}
