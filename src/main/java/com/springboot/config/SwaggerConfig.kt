package com.springboot.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

/**
 * SwaggerConfig
 */
@Configuration
@EnableSwagger2
open class SwaggerConfig {

    /**
     * 可以定义多个组，比如本类中定义把test和demo区分开了
     * （访问页面就可以看到效果了）
     */
    @Bean
    open fun testApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.springboot"))
                .paths(PathSelectors.any())
                .build()
    }


    private fun apiInfo(): ApiInfo {
        val apiInfo = ApiInfo("demo for SpringBoot", //大标题
                "SpringBoot + swagger + mybatis-generator + druid, uses kotlin hybrid with java", //小标题
                "1.0", //版本
                "NO terms of service",
                "crazy0x0419@gmail.com", //作者
                "crazy0x", //链接显示文字
                "http://crazy0x.github.io/"//网站链接
        )
        return apiInfo
    }
}