package com.cdm.controller;

import com.cdm.model.Matche;
import com.cdm.repository.MatchRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping( "/match" )
public class MatchsController
{
    /**
     * Logger
     */
    private static final Logger logger = LogManager.getLogger( MatchsController.class );

    @Autowired
    MatchRepository matchRepository;

    @RequestMapping( "/all" )
    public ResponseEntity getAllMatches()
    {
        return new ResponseEntity( matchRepository.findAll(), HttpStatus.OK );
    }

    @RequestMapping( "/group/{groupe}" )
    public ResponseEntity getMatchesPerGroup( @PathVariable( "groupe" ) int groupe )
    {
        List<Matche> matches = matchRepository.findByGroupeId( groupe );
        return new ResponseEntity( matches, HttpStatus.OK );
    }

    @RequestMapping( "/group/all" )
    public ResponseEntity getMatchesPerGroups()
    {
        List<Matche> matches = matchRepository.findAll();

        TreeMap<Date, List<Matche>> matchesGroupByDate = new TreeMap<>( matches.stream().collect( Collectors.groupingBy( Matche::getDateMatch, Collectors.toList() ) ) );

        return new ResponseEntity( matchesGroupByDate, HttpStatus.OK );
    }
}
