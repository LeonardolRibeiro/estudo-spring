package com.leolaia.estudospring.services;

import com.leolaia.estudospring.models.Person;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonService.class.getName());


    public Person findById(){
        logger.info("Finding one person!");
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Leonardo");
        person.setLastName("Laia");
        person.setAddress("Rua qualquer - Colombo - Paraná");
        person.setGender("Male");
        return person;
    }
}
