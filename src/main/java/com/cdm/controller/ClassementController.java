package com.cdm.controller;

import com.cdm.model.Classement;
import com.cdm.model.Matche;
import com.cdm.model.Pronostic;
import com.cdm.model.User;
import com.cdm.pojo.ClassementPojo;
import com.cdm.repository.ClassementRepository;
import com.cdm.repository.MatchRepository;
import com.cdm.repository.PronosticRepository;
import com.cdm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping( "/classement" )
public class ClassementController
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private PronosticRepository pronosticRepository;

    @Autowired
    private ClassementRepository classementRepository;

    @GetMapping
    public ResponseEntity getClassement ()
    {
        List<User> users = userRepository.findClassement();
        List<ClassementPojo> classementPojos = new ArrayList<>();
        users.stream().forEach( u ->
        {
            Classement classement = u.getClassement();
            classementPojos.add(
                    new ClassementPojo( u.getUsername(), classement.getNbScoreExact(), classement.getNbEquipeGagnante(),
                            classement.getPoints() ) );
        } );

        return new ResponseEntity( classementPojos, HttpStatus.OK );
    }

    @RequestMapping( name = "/calcul" )
    public ResponseEntity calculerClassement ()
    {
        // Liste des matches pour le calcul des points
        // TODO : Récupérer seulement les matchs déjà passés
        List<Matche> matches = matchRepository.findAll();

        // Liste des utilisateurs de l'application pour le calcul des points
        List<User> users = userRepository.findAll();

        // Pour chaque utilisateur
        users.stream().forEach( u ->
        {
            // Récupération du classement du user
            Classement classement = u.getClassement();

            // Récupération des pronostics du user
            List<Pronostic> pronostics = pronosticRepository.findByUserId( u.getId() );

            // Détermination des scores
            classement.determinerScore( pronostics );

            // Calcul des points
            classement.calculPoints();
        } );

        return new ResponseEntity( "Calcul OK", HttpStatus.OK );
    }
}
