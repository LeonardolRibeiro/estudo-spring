package com.leolaia.estudospring.services;

import com.leolaia.estudospring.controllers.PersonController;
import com.leolaia.estudospring.data.vo.v1.PersonVO;
import com.leolaia.estudospring.data.vo.v2.PersonVOV2;
import com.leolaia.estudospring.exceptions.RequiredObjectIsNullException;
import com.leolaia.estudospring.exceptions.ResourceNotFoundException;
import com.leolaia.estudospring.mappers.DozerMapper;
import com.leolaia.estudospring.mappers.custom.PersonMapper;
import com.leolaia.estudospring.models.Person;
import com.leolaia.estudospring.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonService {

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    PersonRepository repository;
    @Autowired
    PersonMapper mapper;


    public List<PersonVO> findAll() {
        logger.info("Finding all people!");
        List<PersonVO> personVOS = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
        personVOS
                .stream().
                forEach(p -> {
                    try {
                        p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        return personVOS;
    }
    public PersonVO findById(Long id) {
        logger.info("Finding one person!");
        Person person = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        PersonVO vo = DozerMapper.parseObject(person, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public PersonVO create(PersonVO person) {

        if(person == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one person!");
        Person entity = DozerMapper.parseObject(person, Person.class);
        PersonVO vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVOV2 createV2(PersonVOV2 person) {
        logger.info("Creating one person with V2!");
        Person entity = mapper.convertVOToEntity(person);
        PersonVOV2 vo = mapper.convertEntityToVo(repository.save(entity));
        return vo;
    }

    public PersonVO update(PersonVO person) {
        if(person == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one person!");
        Person entity = repository.findById(person.getKey()).orElseThrow( () -> new ResourceNotFoundException("No records found for this ID"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        PersonVO vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");
        Person entity = repository.findById(id).orElseThrow( () -> new ResourceNotFoundException("No records found for this ID"));
        repository.delete(entity);
    }

}
