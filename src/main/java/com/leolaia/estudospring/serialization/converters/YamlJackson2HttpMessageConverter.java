package com.leolaia.estudospring.serialization.converters;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

import static com.leolaia.estudospring.utils.MediaType.APPLICATION_YML;

public class YamlJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {

    public YamlJackson2HttpMessageConverter() {
        super(new YAMLMapper().setSerializationInclusion(
                JsonInclude.Include.NON_NULL), // Isso define que os campos nulos não serão serizalizados
                MediaType.parseMediaType(APPLICATION_YML));
    }
}
