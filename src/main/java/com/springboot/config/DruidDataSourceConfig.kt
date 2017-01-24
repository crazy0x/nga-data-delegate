package com.springboot.config

import java.sql.SQLException

import javax.sql.DataSource

import org.springframework.boot.bind.RelaxedPropertyResolver
import org.springframework.context.EnvironmentAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.transaction.annotation.EnableTransactionManagement

import com.alibaba.druid.pool.DruidDataSource

/**
 * Druid的DataResource配置类
 */
@Configuration
@EnableTransactionManagement
open class DruidDataSourceConfig : EnvironmentAware {

    private var propertyResolver: RelaxedPropertyResolver? = null

    override fun setEnvironment(env: Environment) {
        this.propertyResolver = RelaxedPropertyResolver(env, "spring.datasource.")
    }

    @Bean
    open fun dataSource(): DataSource {
        println("注入druid！！！")
        val datasource = DruidDataSource()
        datasource.url = propertyResolver!!.getProperty("url")
        datasource.driverClassName = propertyResolver!!.getProperty("driver-class-name")
        datasource.username = propertyResolver!!.getProperty("username")
        datasource.password = propertyResolver!!.getProperty("password")
        datasource.initialSize = Integer.valueOf(propertyResolver!!.getProperty("initial-size"))!!
        datasource.minIdle = Integer.valueOf(propertyResolver!!.getProperty("min-idle"))!!
        datasource.maxWait = java.lang.Long.valueOf(propertyResolver!!.getProperty("max-wait"))!!
        datasource.maxActive = Integer.valueOf(propertyResolver!!.getProperty("max-active"))!!
        datasource.minEvictableIdleTimeMillis = java.lang.Long.valueOf(propertyResolver!!.getProperty("min-evictable-idle-time-millis"))!!
        try {
            datasource.setFilters("stat,wall")
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return datasource
    }
}
