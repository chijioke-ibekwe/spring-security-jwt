package dev.chijiokeibekwe.jwtsecurity.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${server.host}")
    private String host;

    @Bean
    public OpenAPI definition() {
        Server server = new Server();
        server.setUrl(this.host);
        server.setDescription("Development");

        Contact contact = new Contact();
        contact.setName("Chijioke Ibekwe");
        contact.setEmail("ibekwe.chijioke18@gmail.com");

        return new OpenAPI()
                .info(new Info()
                        .title("Spring Security JWT Starter APIs")
                        .version("1.0.0")
                        .description("APIs exposing the endpoints for the spring security JWT starter project")
                        .contact(contact)
                )
                .servers(List.of(server))
                .components(new Components().addSecuritySchemes("bearer-token",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER).name("Authorization")))
                .addSecurityItem(
                        new SecurityRequirement().addList("bearer-token", Arrays.asList("read", "write")));
    }
}
