package com.leolaia.estudospring.integrationTests.controllers.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leolaia.estudospring.integrationTests.testcontainers.AbstractIntegrationTest;
import com.leolaia.estudospring.integrationTests.vo.PersonVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static com.leolaia.estudospring.configs.TestConfigs.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static PersonVO person;

    @BeforeAll
    public static void setUp(){
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); //Nosso VO nao tem as propriedades do HATEOAS, entao para ignoralas no parse adicionamos essa propriedade

        person = new PersonVO();
    }

    @Test
    @Order(1)
    public void createPerson() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(HEADER_PARAM_ORIGIN, "https://laia.com.br")
                .setBasePath("api/person/v1")
                .setPort(SERVER_PORT)
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                    .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given()
                        .spec(specification)
                        .contentType(CONTENT_TYPE_JSON)
                        .body(person)
                    .when()
                        .post()
                    .then()
                        .statusCode(200)
                        .extract()
                        .body().asString();

        PersonVO createdPerson = objectMapper.readValue(content, PersonVO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirst_name());
        assertNotNull(createdPerson.getLast_name());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());

        assertTrue(createdPerson.getId() > 0);
        assertEquals("Leo", createdPerson.getFirst_name() );
        assertEquals("Laia", createdPerson.getLast_name());
        assertEquals("Colombo, Paraná, BR", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());

    }




    private void mockPerson() {
        person.setFirst_name("Leo");
        person.setLast_name("Laia");
        person.setAddress("Colombo, Paraná, BR");
        person.setGender("Male");
    }


}
