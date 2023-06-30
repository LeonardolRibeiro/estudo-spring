package com.leolaia.estudospring.configs;

import com.leolaia.estudospring.serialization.converters.YamlJackson2HttpMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final MediaType MEDIA_TYPE_APPLICATION_YML = MediaType.valueOf("application/x-yaml");
    @Value("${cors.originPatterns:default}") // Essa anotação faz o spring procurar essa propriedade no arquivo application.properties e inicializar o valor
    private String corsOriginPatterns = "";

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new YamlJackson2HttpMessageConverter());
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        //VIA HEADER PARAM: http://localhost:8080/api/person/v1
        // O nome do parâmetro via header é Accept e o valor deve ser: application/xml ou application/json
        configurer.favorParameter(false)
                .ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                //formatos suportados
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("x-yaml", MEDIA_TYPE_APPLICATION_YML);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        var allowedOrigins = corsOriginPatterns.split(",");
        //Se atentar para não passar ' ' (espaços) entre as origens permitidas no arquivo application.properties

        registry.addMapping("/**") //Liberamos acesso a todos os serviços de nossa api
            .allowedMethods("*")//.allowedMethods("GET", "POST", "PUT")
            .allowedOrigins(allowedOrigins)
            .allowCredentials(true);
    }
}
