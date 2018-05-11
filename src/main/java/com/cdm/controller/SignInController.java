package com.cdm.controller;

import com.cdm.model.User;
import com.cdm.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.NonUniqueResultException;
import java.util.Optional;

@RestController( "signinController" )
@RequestMapping( "/signin" )
public class SignInController
{
    /**
     * Logger
     */
    private static final Logger logger = LogManager.getLogger( SignInController.class );

    @Autowired
    private UserRepository userRepository;

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity signin( @RequestBody User user )
    {
        try
        {
            Optional<User> optinalUser = userRepository.findByMail( user.getMail() );

            // Passage de la verification à true
            user.setVerification( true );

            // Sauvegarde en base du user
            userRepository.save( user );

            return new ResponseEntity( user, HttpStatus.CREATED );
        }
        catch ( NonUniqueResultException | IncorrectResultSizeDataAccessException nure )
        {
            logger.error( "User already exists {}", () -> user.getMail() );
            return new ResponseEntity( "User already exists", HttpStatus.FOUND );
        }
    }
}
