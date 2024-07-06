package org.yasmingv.ms_payment.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    // http://host.docker.internal:52141/swagger-ui/index.html

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()

                .info(
                        new Info()
                                .title("Ms Payment")
                                .description("Micro-serviço responsável por simular o pagamento de uma compra do cartão de crédito do cliente")
                                .version("v1")
                );
    }

}
