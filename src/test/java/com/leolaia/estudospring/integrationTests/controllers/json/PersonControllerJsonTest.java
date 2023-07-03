package com.leolaia.estudospring.integrationTests.controllers.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leolaia.estudospring.configs.TestConfigs;
import com.leolaia.estudospring.integrationTests.testcontainers.AbstractIntegrationTest;
import com.leolaia.estudospring.integrationTests.vo.AccountCredentialsVO;
import com.leolaia.estudospring.integrationTests.vo.PersonVO;
import com.leolaia.estudospring.integrationTests.vo.TokenVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
    @Order(0)
    public void authorization() throws JsonMappingException, JsonProcessingException {
        AccountCredentialsVO user = new AccountCredentialsVO("leandro", "teste");

        var accessToken = given()
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(user)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenVO.class)
                .getAccessToken();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    public void testCreate() throws JsonMappingException, JsonProcessingException {
        mockPerson();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(person)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
        person = persistedPerson;

        assertNotNull(persistedPerson);

        assertNotNull(persistedPerson.getId());
        assertNotNull(persistedPerson.getFirst_name());
        assertNotNull(persistedPerson.getLast_name());
        assertNotNull(persistedPerson.getAddress());
        assertNotNull(persistedPerson.getGender());
        assertTrue(persistedPerson.getEnabled());

        assertTrue(persistedPerson.getId() > 0);

        assertEquals("Gustavo", persistedPerson.getFirst_name());
        assertEquals("Henrique", persistedPerson.getLast_name());
        assertEquals("Parque, Quatro Barras, PR", persistedPerson.getAddress());
        assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(2)
    public void disable() throws JsonMappingException, JsonProcessingException {

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", person.getId())
                .when()
                .patch("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
        person = persistedPerson;

        assertNotNull(persistedPerson);

        assertNotNull(persistedPerson.getId());
        assertNotNull(persistedPerson.getFirst_name());
        assertNotNull(persistedPerson.getLast_name());
        assertNotNull(persistedPerson.getAddress());
        assertNotNull(persistedPerson.getGender());
        assertFalse(persistedPerson.getEnabled());

        assertEquals(person.getId(), persistedPerson.getId());

        assertEquals("Gustavo", persistedPerson.getFirst_name());
        assertEquals("Henrique", persistedPerson.getLast_name());
        assertEquals("Parque, Quatro Barras, PR", persistedPerson.getAddress());
        assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(3)
    public void testUpdate() throws JsonMappingException, JsonProcessingException {
        person.setLast_name("Sobrenome");

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(person)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
        person = persistedPerson;

        assertNotNull(persistedPerson);

        assertNotNull(persistedPerson.getId());
        assertNotNull(persistedPerson.getFirst_name());
        assertNotNull(persistedPerson.getLast_name());
        assertNotNull(persistedPerson.getAddress());
        assertNotNull(persistedPerson.getGender());
        assertFalse(persistedPerson.getEnabled());

        assertEquals(person.getId(), persistedPerson.getId());

        assertEquals("Gustavo", persistedPerson.getFirst_name());
        assertEquals("Sobrenome", persistedPerson.getLast_name());
        assertEquals("Parque, Quatro Barras, PR", persistedPerson.getAddress());
        assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(4)
    public void testFindById() throws JsonMappingException, JsonProcessingException {
        mockPerson();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
        person = persistedPerson;

        assertNotNull(persistedPerson);

        assertNotNull(persistedPerson.getId());
        assertNotNull(persistedPerson.getFirst_name());
        assertNotNull(persistedPerson.getLast_name());
        assertNotNull(persistedPerson.getAddress());
        assertNotNull(persistedPerson.getGender());
        assertFalse(persistedPerson.getEnabled());

        assertEquals(person.getId(), persistedPerson.getId());

        assertEquals("Gustavo", persistedPerson.getFirst_name());
        assertEquals("Sobrenome", persistedPerson.getLast_name());
        assertEquals("Parque, Quatro Barras, PR", persistedPerson.getAddress());
        assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(5)
    public void testDelete() throws JsonMappingException, JsonProcessingException {
        mockPerson();

        given().spec(specification)
                .pathParam("id", person.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }


    @Test
    @Order(6)
    public void testFindAll() throws JsonMappingException, JsonProcessingException {

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        List<PersonVO> people = objectMapper.readValue(content, new TypeReference<List<PersonVO>>() {});

        PersonVO foundPersonOne = people.get(0);

        assertNotNull(foundPersonOne.getId());
        assertNotNull(foundPersonOne.getFirst_name());
        assertNotNull(foundPersonOne.getLast_name());
        assertNotNull(foundPersonOne.getAddress());
        assertNotNull(foundPersonOne.getGender());
        assertTrue(foundPersonOne.getEnabled());

        assertEquals(1, foundPersonOne.getId());

        assertEquals("Leonardo", foundPersonOne.getFirst_name());
        assertEquals("Laia", foundPersonOne.getLast_name());
        assertEquals("Rua qualquer - Colombo - Paraná", foundPersonOne.getAddress());
        assertEquals("Male", foundPersonOne.getGender());

        PersonVO foundPersonFour = people.get(3);

        assertNotNull(foundPersonFour.getId());
        assertNotNull(foundPersonFour.getFirst_name());
        assertNotNull(foundPersonFour.getLast_name());
        assertNotNull(foundPersonFour.getAddress());
        assertNotNull(foundPersonFour.getGender());
        assertTrue(foundPersonFour.getEnabled());

        assertEquals(4, foundPersonFour.getId());

        assertEquals("Vera", foundPersonFour.getFirst_name());
        assertEquals("Antunes", foundPersonFour.getLast_name());
        assertEquals("Rua qualquer - Colombo - Paraná", foundPersonOne.getAddress());
        assertEquals("Female", foundPersonFour.getGender());
    }

    @Test
    @Order(7)
    public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {

        RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        given().spec(specificationWithoutToken)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .when()
                .get()
                .then()
                .statusCode(403);
    }

    private void mockPerson() {
        person.setFirst_name("Gustavo");
        person.setLast_name("Henrique");
        person.setAddress("Parque, Quatro Barras, PR");
        person.setGender("Male");
        person.setEnabled(true);
    }


}
