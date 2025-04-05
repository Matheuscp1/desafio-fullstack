package com.simplesdental.product.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openApi() {

        return new OpenAPI()
                .info(new Info()
                        .title("Produto")
                        .version("v1")
                        .description("Gerencia produtos"))
                .tags(Arrays.asList(
                                new Tag().name("Produtos").description("Gerencia produtos"),
                                new Tag().name("Categoria").description("Gerencia categoria"),
                                new Tag().name("Produtos-v2").description("Gerencia produtos v2")
                        )
                );
    }

}
