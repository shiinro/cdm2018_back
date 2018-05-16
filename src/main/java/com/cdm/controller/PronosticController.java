package com.cdm.controller;

import com.cdm.model.Equipe;
import com.cdm.model.Matche;
import com.cdm.model.Pronostic;
import com.cdm.model.User;
import com.cdm.pojo.PronosticPojo;
import com.cdm.pojo.PronosticRequestBody;
import com.cdm.repository.MatchRepository;
import com.cdm.repository.PronosticRepository;
import com.cdm.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
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

    @Autowired
    private UserRepository userRepository;

    @GetMapping( "/{userId}" )
    public ResponseEntity getPronosticForUser ( @PathVariable( "userId" ) Integer userId )
    {
        // Récupération des pronostics
        List<Pronostic> pronostics = pronosticRepository.findByUserId( userId );

        // Récupération de la liste des matches
        List<Matche> matches = matchRepository.findAll();

        // Création de l'objet à renvoyer dans le requête
        List<PronosticPojo> pronosticPojos = new ArrayList<>();
        int i = 1;
        for ( Matche match : matches )
        {
            Optional<Pronostic> optionalPronostic = pronostics.stream().filter(
                    p -> p.getMatch().getEquipe1().getNom().equals( match.getEquipe1().getNom() ) &&
                            p.getMatch().getEquipe2().getNom().equals( match.getEquipe2().getNom() ) ).findFirst();
            Pronostic pronostic = null;
            try
            {
                pronostic = optionalPronostic.get();
            }
            catch ( NoSuchElementException e )
            {
                logger.debug( "Aucun prono pour le match {} : {} - {}", match.getDateMatch(),
                        match.getEquipe1().getNom(), match.getEquipe2().getNom() );
            }
            pronosticPojos.add( new PronosticPojo( i, match, pronostic ) );
            i++;
        }

        // Trie des pronostic par date
        TreeMap<String, List<PronosticPojo>> pronosticPojoGroupByDate = new TreeMap<>( pronosticPojos.stream().collect(
                Collectors.groupingBy( PronosticPojo::getJourMatch, Collectors.toList() ) ) );

        return new ResponseEntity( pronosticPojoGroupByDate, HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity pronostics ( @RequestBody PronosticRequestBody pronosticRequestBody )
    {
        // Récupération du user
        User user = getUser( pronosticRequestBody.getUser().getId() );

        // Récupération de la date
        LocalDate now = LocalDate.now();

        // Récupération de la liste des pronos envoyé par le client
        Map<String, List<PronosticPojo>> map = pronosticRequestBody.getPronostics();
        for ( List<PronosticPojo> pronosticPojos : map.values() )
        {
            List<PronosticPojo> pronosticPojoList = getPronosticAvailable( now, pronosticPojos );
            for ( PronosticPojo pronosticPojo : pronosticPojoList )
            {
                // Récupération du match
                Equipe equipeHome = pronosticPojo.getEquipeHome();
                Equipe equipeAway = pronosticPojo.getEquipeAway();
                Matche match = matchRepository.findByEquipe1AndEquipe2( equipeHome, equipeAway );

                if ( match != null )
                {
                    // Récupération du pronostic s'il existe
                    Pronostic pronostic = pronosticRepository.findByUserIdAndMatch( user.getId(), equipeHome.getNom(),
                            equipeAway.getNom() );

                    if ( pronostic == null )
                    {
                        // Création du pronostic si non trouvé
                        pronostic = new Pronostic();
                        pronostic.setUser( user );
                        pronostic.setMatch( match );
                        pronostic.setScoreHome( pronosticPojo.getScoreEquipeHome() );
                        pronostic.setScoreAway( pronosticPojo.getScoreEquipeAway() );
                    }
                    else
                    {
                        pronostic.setScoreHome( pronosticPojo.getScoreEquipeHome() );
                        pronostic.setScoreAway( pronosticPojo.getScoreEquipeAway() );
                    }

                    // Sauvegarde du pronostic en bdd
                    pronosticRepository.save( pronostic );
                }
            }
        }

        // Envoie de la réponse
        return new ResponseEntity( HttpStatus.OK );
    }

    /**
     * Récupération du user en base
     *
     * @param userId - Id de l'user envoyé
     * @return L'utilisateur
     */
    private User getUser ( Integer userId )
    {
        // Récupération du user en base
        Optional<User> optinalUser = userRepository.findById( userId );
        User user = null;
        try
        {
            user = optinalUser.get();
        }
        catch ( NoSuchElementException e )
        {
            logger.debug( "Aucun user trouvé {}", userId );
        }
        return user;
    }

    /**
     * Filtrage des pronostics envoyé par le client. On ne garde que ceux dont le match n'est pas dépassé
     *
     * @param dateReference  - Date de référence pour la liste des pronos
     * @param pronosticPojos - Liste des pronos à filtrer
     * @return La liste des pronos filtrés
     */
    private List<PronosticPojo> getPronosticAvailable ( LocalDate dateReference, List<PronosticPojo> pronosticPojos )
    {
        return pronosticPojos.stream().filter(
                p -> dateReference.isAfter(
                        p.getDateMatch().toInstant().atZone( ZoneId.systemDefault() ).toLocalDate() ) ).collect(
                Collectors.toList() );
    }
}
