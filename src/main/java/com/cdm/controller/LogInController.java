package com.cdm.controller;

import com.cdm.model.User;
import com.cdm.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController( "loginController" )
@RequestMapping( "/login" )
public class LogInController
{
    /**
     * Logger
     */
    private static final Logger logger = LogManager.getLogger( LogInController.class );

    @Autowired
    private UserRepository userRepository;

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity login( @RequestBody User user )
    {
        Optional<User> optionalUser = userRepository.findByMail( user.getMail() );
        User userInBase = null;
        try
        {
            userInBase = optionalUser.get();
        }
        catch ( NoSuchElementException nsee )
        {
            logger.error( "Connexion fail sur le mail {}", () -> user.getMail() );
            return new ResponseEntity( "User not found", HttpStatus.NO_CONTENT );
        }
        String token = generateToken().toString();
        if ( token.equals( "" ) )
        {
            return new ResponseEntity( "Token invalid", HttpStatus.PARTIAL_CONTENT );
        }
        else
        {
            userInBase.setToken( token );
        }
        return new ResponseEntity( userInBase, HttpStatus.OK );
    }

    /**
     * Génération d'un token pour l'api
     *
     * @return un token généré aléatoirement
     */
    private char[] generateToken()
    {
        char[] digits = new char[ 16 ];
        SecureRandom srnd = null;
        try
        {
            srnd = SecureRandom.getInstance( "SHA1PRNG" );
        }
        catch ( NoSuchAlgorithmException e )
        {
            logger.error( "aucun algorithme connu", e );
            return digits;
        }
        for ( int i = 0; i < digits.length; i++ )
        {
            digits[ i ] = ( char ) ( '0' + srnd.nextInt( 9 ) );
        }
        return digits;
    }
}
