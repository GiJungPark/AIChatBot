package io.gijung.springboot.presentation.api.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        val info: Info = Info()
            .version("0.0.1")
            .title("ChatBot API")
            .description("챗봇 API 명세서")

        return OpenAPI()
            .info(info)
    }
}