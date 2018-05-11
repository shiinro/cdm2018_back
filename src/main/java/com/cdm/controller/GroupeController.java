package com.cdm.controller;

import com.cdm.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/group" )
public class GroupeController
{
    @Autowired
    private GroupRepository groupRepository;

    @RequestMapping( "/all" )
    public ResponseEntity getAllGroupes()
    {
        return new ResponseEntity( groupRepository.findAll(), HttpStatus.OK );
    }

}
