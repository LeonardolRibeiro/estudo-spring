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
import com.leolaia.estudospring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserService implements UserDetailsService {

    private Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Finding one user by name " + username + "!");
        var user = repository.findByUsername(username);
        if(user != null){
            return user;
        }else {
            throw new UsernameNotFoundException("Username: " + username + " not found!");
        }
    }
}
