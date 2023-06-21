package com.leolaia.estudospring.unittests.mockito.services;

import com.leolaia.estudospring.data.vo.v1.PersonVO;
import com.leolaia.estudospring.models.Person;
import com.leolaia.estudospring.repositories.PersonRepository;
import com.leolaia.estudospring.services.PersonService;
import com.leolaia.estudospring.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    MockPerson input;

    @InjectMocks
    private PersonService service;

    @Mock
    PersonRepository repository;
    @BeforeEach
    void setUpMocks() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
        Person person = input.mockEntity(1);
        person.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(person));

        try {
            PersonVO result = service.findById(1L);
            assertNotNull(result);
            assertNotNull(result.getKey());
            assertNotNull(result.getLinks());
            assertNotNull(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
            assertEquals("Addres Test1", result.getAddress());
            assertEquals("First Name Test1", result.getFirstName());
            assertEquals("Last Name Test1", result.getLastName());
            assertEquals("Female", result.getGender());

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}