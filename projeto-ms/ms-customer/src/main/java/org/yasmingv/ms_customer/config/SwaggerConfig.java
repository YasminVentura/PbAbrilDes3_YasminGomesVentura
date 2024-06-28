package org.yasmingv.ms_customer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    // http://host.docker.internal:58332/swagger-ui/index.html

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()

                .info(
                        new Info()
                                .title("Ms Customers")
                                .description("Micro-serviço responsável por armazenar os dados do usuário e o seu total de pontos acumulados")
                                .version("v1")
                );
    }

}
