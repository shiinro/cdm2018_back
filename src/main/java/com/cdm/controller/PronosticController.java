package com.cdm.controller;

import com.cdm.model.Matche;
import com.cdm.model.Pronostic;
import com.cdm.pojo.PronosticPojo;
import com.cdm.repository.MatchRepository;
import com.cdm.repository.PronosticRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping( "/pronostic" )
public class PronosticController
{
    /**
     * Logger
     */
    private static final Logger logger = LogManager.getLogger( PronosticController.class );

    @Autowired
    private PronosticRepository pronosticRepository;

    @Autowired
    private MatchRepository matchRepository;

    @GetMapping( "/{userId}" )
    public ResponseEntity getPronosticForUser( @PathVariable( "userId" ) Integer userId )
    {
        List<Pronostic> pronostics = pronosticRepository.findByUserId( userId );
        List<Matche> matches = matchRepository.findAll();

        List<PronosticPojo> pronosticPojos = new ArrayList<>();

        int i = 1;
        for ( Matche match : matches )
        {
            Optional<Pronostic> optionalPronostic = pronostics.stream().filter( p -> p.getMatch().equals( match ) ).findFirst();
            Pronostic pronostic = null;
            try
            {
                pronostic = optionalPronostic.get();
            }
            catch ( NoSuchElementException e )
            {
                logger.debug( "Aucun prono pour le match {}", match );
            }
            pronosticPojos.add( new PronosticPojo( i, match, pronostic ) );
            i++;
        }

        TreeMap<Date, List<PronosticPojo>> pronosticPojoGroupByDate = new TreeMap<>( pronosticPojos.stream().collect( Collectors.groupingBy( PronosticPojo::getDateMatch, Collectors.toList() ) ) );

        return new ResponseEntity( pronosticPojoGroupByDate, HttpStatus.OK );
    }
}
