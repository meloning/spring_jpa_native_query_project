package com.example.spring_jpa_native_query_project.infra.config

import javax.persistence.EntityManagerFactory
import javax.sql.DataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = ["com.example.spring_jpa_native_query_project.infra.entity", "com.example.spring_jpa_native_query_project.infra.repository"],
    entityManagerFactoryRef = "defaultEntityManagerFactory",
    transactionManagerRef = "defaultTransactionManager"
)
class DefaultDbConfig {
    @Bean(name = ["defaultEntityDataSource"])
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    fun dataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @ConfigurationProperties(prefix = "spring.jpa")
    @Bean(name = ["defaultJpaProperties"])
    @Primary
    fun jpaProperties(): JpaProperties {
        return JpaProperties()
    }

    @ConfigurationProperties(prefix = "spring.jpa.hibernate")
    @Bean(name = ["defaultHibernateProperties"])
    @Primary
    fun hibernateProperties(): HibernateProperties {
        return HibernateProperties()
    }

    @Bean(name = ["defaultEntityManagerFactory"])
    @Primary
    fun defaultEntityManagerFactory(builder: EntityManagerFactoryBuilder, @Qualifier("defaultEntityDataSource") dataSource: DataSource, @Qualifier("defaultJpaProperties") jpaProperties: JpaProperties, @Qualifier("defaultHibernateProperties") hibernateProperties: HibernateProperties): LocalContainerEntityManagerFactoryBean {
        val properties = hibernateProperties.determineHibernateProperties(jpaProperties.properties, HibernateSettings())
        return builder.dataSource(dataSource).properties(properties).packages("com.example.spring_jpa_native_query_project.infra.entity", "com.example.spring_jpa_native_query_project.infra.repository").build()
    }

    @Bean(name = ["defaultTransactionManager"])
    @Primary
    fun defaultTransactionManager(@Qualifier("defaultEntityManagerFactory") entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }
}
