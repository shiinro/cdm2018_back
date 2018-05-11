package com.cdm.controller;

import com.cdm.repository.EquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/team" )
public class EquipeController
{
    @Autowired
    private EquipeRepository equipeRepository;

    @RequestMapping( "/all" )
    public ResponseEntity getAllEquipes()
    {
        return new ResponseEntity( equipeRepository.findAll(), HttpStatus.OK );
    }
}
