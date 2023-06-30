package com.leolaia.estudospring.integrationTests.controllers.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leolaia.estudospring.configs.TestConfigs;
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
                .addHeader(HEADER_PARAM_ORIGIN, ORIGIN_DOMAIN)
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

        PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
        person = persistedPerson;

        assertNotNull(persistedPerson.getId());
        assertNotNull(persistedPerson.getFirst_name());
        assertNotNull(persistedPerson.getLast_name());
        assertNotNull(persistedPerson.getAddress());
        assertNotNull(persistedPerson.getGender());

        assertTrue(persistedPerson.getId() > 0);
        assertEquals("Leo", persistedPerson.getFirst_name() );
        assertEquals("Laia", persistedPerson.getLast_name());
        assertEquals("Colombo, Paraná, BR", persistedPerson.getAddress());
        assertEquals("Male", persistedPerson.getGender());

    }

    @Test
    @Order(2)
    public void createPersonWithNotAlowedOrigin() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(HEADER_PARAM_ORIGIN, ORIGIN_UNKNOW_DOMAIN)
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
                        .statusCode(403)
                        .extract()
                        .body().asString();

        assertNotNull(content);
        assertEquals("Invalid CORS request", content);

    }

    @Test
    @Order(3)
    public void testFindById() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(HEADER_PARAM_ORIGIN, ORIGIN_DOMAIN)
                .setBasePath("api/person/v1")
                .setPort(SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given()
                        .spec(specification)
                        .contentType(CONTENT_TYPE_JSON)
                        .pathParam("id", person.getId())
                    .when()
                        .get("{id}")
                    .then()
                        .statusCode(200)
                    .extract()
                        .body().asString();

        PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
        person = persistedPerson;

        assertNotNull(persistedPerson.getId());
        assertNotNull(persistedPerson.getFirst_name());
        assertNotNull(persistedPerson.getLast_name());
        assertNotNull(persistedPerson.getAddress());
        assertNotNull(persistedPerson.getGender());

        assertTrue(persistedPerson.getId() > 0);
        assertEquals("Leo", persistedPerson.getFirst_name() );
        assertEquals("Laia", persistedPerson.getLast_name());
        assertEquals("Colombo, Paraná, BR", persistedPerson.getAddress());
        assertEquals("Male", persistedPerson.getGender());

    }

    @Test
    @Order(4)
    public void testFindByIdWithNotAlowedOrigin() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(HEADER_PARAM_ORIGIN, ORIGIN_UNKNOW_DOMAIN)
                .setBasePath("api/person/v1")
                .setPort(SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given()
                .spec(specification)
                .contentType(CONTENT_TYPE_JSON)
                .pathParam("id", person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body().asString();

        assertNotNull(content);
        assertEquals("Invalid CORS request", content);
    }




    private void mockPerson() {
        person.setFirst_name("Leo");
        person.setLast_name("Laia");
        person.setAddress("Colombo, Paraná, BR");
        person.setGender("Male");
    }


}
