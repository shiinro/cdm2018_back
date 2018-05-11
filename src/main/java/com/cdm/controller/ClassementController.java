package com.cdm.controller;

import com.cdm.model.User;
import com.cdm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/classement" )
public class ClassementController
{
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity getClassement()
    {
        List<User> users = userRepository.findClassement();
        return new ResponseEntity( users, HttpStatus.OK );
    }
}
