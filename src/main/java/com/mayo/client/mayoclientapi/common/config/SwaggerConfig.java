package com.mayo.client.mayoclientapi.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("mayo Client API 서버")
                        .description("mayo Client API 명세서 입니다.")
                        .version("v1.0.0"));
    }
}