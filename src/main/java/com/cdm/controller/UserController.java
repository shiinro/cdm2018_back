package com.cdm.controller;

import com.cdm.model.User;
import com.cdm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/user" )
public class UserController
{
    @Autowired
    private UserRepository userRepository;

    @RequestMapping( method = RequestMethod.GET )
    public User getUser( @RequestParam( "name" ) String name )
    {
        return userRepository.findByUsername( name );
    }
}

