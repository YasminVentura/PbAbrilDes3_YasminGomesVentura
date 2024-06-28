package org.yasmingv.ms_calculate.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()

                .info(
                        new Info()
                                .title("Ms Calculate")
                                .description("Micro-serviço responsável por calcular o total de pontos que irá ser acumulado em uma compra")
                                .version("v1")
                );
    }

}
