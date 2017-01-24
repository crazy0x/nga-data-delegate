package com.springboot

import javax.servlet.MultipartConfigElement

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.MultipartConfigFactory
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean

@MapperScan("com.springboot.model.mapper")
@SpringBootApplication
open class Application : SpringBootServletInitializer() {
	@Bean
	open fun multipartConfigElement() : MultipartConfigElement{
		var factory = MultipartConfigFactory()
		factory.setMaxFileSize("5MB")
		factory.setMaxRequestSize("5MB")
		return factory.createMultipartConfig()
	}

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java)
        }
    }
}
