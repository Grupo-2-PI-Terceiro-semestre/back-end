package sptech.school.order_hub.config.security.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("OrderHub API")
                        .description("API de integração com o OrderHub. Visite o nosso [site](https://aplicacao.eastus2.azurecontainer.io")
                        .version("v0.0.1")
                        .contact(new Contact()
                                .name("Suporte OrderHub")
                                .url("https://aplicacao.eastus2.azurecontainer.io")
                                .email("suporte@orderhub.com.br"))
                )
                .addTagsItem(new Tag().name("Usuario").description("Endpoints relacionados a Usuario"))
                .addTagsItem(new Tag().name("Cliente").description("Endpoints relacionados a Cliente"))
                .addSecurityItem(new SecurityRequirement().addList("JavaInUseSecurityScheme"))
                .components(new Components()
                        .addSecuritySchemes("JavaInUseSecurityScheme", new SecurityScheme()
                                .name("JavaInUseSecurityScheme")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))
                );
    }
}