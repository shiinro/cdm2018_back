package com.cdm.controller;

import com.cdm.model.Equipe;
import com.cdm.model.Groupe;
import com.cdm.model.Matche;
import com.cdm.repository.EquipeRepository;
import com.cdm.repository.GroupRepository;
import com.cdm.repository.MatchRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping( "/init" )
public class InitController
{
    /**
     * Logger
     */
    private static final Logger logger = LogManager.getLogger( InitController.class );

    /**
     * Parser pour la date
     */
    private static final DateFormat m_ISO8601Local = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" );

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private EquipeRepository equipeRepository;

    @RequestMapping( "/init" )
    public Response initialiserGroupe()
    {
        try
        {
            // Connexion au site de la FIFA pour récupérer les matches
            Document doc = Jsoup.connect( "http://fr.fifa.com/worldcup/groups/" ).get();

            // Récupération des groupes
            extractGroupe( doc );

            // Récupération des équipes
            extractEquipe( doc );

            // Récupération des matchs
            extractMatch( doc );
        }
        catch ( IOException ioException )
        {
            logger.error( "Impossible de se connecter au site de la FIFA pour initialiser l'application", ioException );
        }
        catch ( Exception e )
        {
            logger.error( "Erreur de connexion ", e );
        }
        return null;
    }

    /**
     * Extraction des matchs sur le site de la FIFA
     *
     * @param doc - document html de la page
     * @throws ParseException
     */
    private void extractMatch( Document doc ) throws ParseException
    {
        logger.info("Récupération des matches");
        Elements expandedRows = doc.select( ".expandrow" );
        for ( Element expandedRow : expandedRows )
        {
            Elements tdListMatch = expandedRow.select( ".fi-table__mu .fi-matchlist .fi-mu-list div[data-id]" );
            for ( Element elementMatch : tdListMatch )
            {
                // Récupération du nom de l'equipe home
                String nomEquipe1 = elementMatch.select( ".home .fi-t__nText" ).text();

                // Récupération du nom de l'équipe away
                String nomEquipe2 = elementMatch.select( ".away .fi-t__nText" ).text();

                // Récupération de la date du match
                String dateMatch = elementMatch.select( ".fi-mu__info__datetime" ).attr( "data-utcdate" );

                // Récupération de la ville du match
                String ville = elementMatch.select( ".fi-mu__info .fi__info__location .fi__info__venue" ).text();

                // Récupération du stade du match
                String stade = elementMatch.select( ".fi-mu__info .fi__info__location .fi__info__stadium" ).text();

                // Récupération du nom du groupe
                String nomGroupe = elementMatch.select( ".fi-mu__info .fi__info__group" ).text();

                logger.info("Nom groupe : {}", nomGroupe);

                // Récupération des equipes
                Equipe equipe1 = recupererEquipe( null, nomEquipe1, null );
                Equipe equipe2 = recupererEquipe( null, nomEquipe2, null );

                // Récupération du groupe
                Groupe groupe = recupererGroupe( nomGroupe );

                // Sauvegarde du match
                recupererMatch( dateMatch, ville, stade, equipe1, equipe2, groupe );
            }
        }
    }

    /**
     * Extraction des groupes sur le site de la FIFA
     *
     * @param doc - document html de la page
     */
    private void extractGroupe( Document doc )
    {
        logger.info("Récupération des groupes");
        // Récupération des groupes
        Elements groupes = doc.select( ".fi-page-nav__menu li" );
        for ( Element elementGroupe : groupes )
        {
            recupererGroupe( elementGroupe.select( "li a span" ).text() );
        }
    }

    /**
     * Récupération des équipes sur le site de la FIFA
     *
     * @param doc - document html de la page
     */
    private void extractEquipe( Document doc )
    {
        logger.info("Récupération des equipes");
        // Récupération de tous les element groupe pour récupérer les équipes
        Elements tableGroupes = doc.select( ".fi-standings-list .fi-table" );
        for ( Element tableGroupe : tableGroupes )
        {
            // Récupération du nom du groupe
            String nomGroupe = tableGroupe.select( ".fi-table__caption .fi-table__caption__title" ).text();
            Groupe groupe = recupererGroupe( nomGroupe );

            // Récupération de l'élément "équipe"
            Elements trEquipes = tableGroupe.select( "tr[data-team-id]" );
            for ( Element trEquipe : trEquipes )
            {
                // Récupération du nom de l'équipe
                String nomEquipe = trEquipe.select( ".fi-table__teamname .fi-t__nText" ).text();

                // Récupération du drapeau de l'équipe
                String flag = trEquipe.select( ".fi-table__teamname .fi-t__i img" ).attr( "src" );

                // Sauvegarde de l'équipe
                Equipe equipe = recupererEquipe( groupe, nomEquipe, flag );
            }
        }
    }

    /**
     * Méthode de récupération ou création d'une equipe en BDD
     *
     * @param groupe    - Groupe à affecter à l'équipe
     * @param nomEquipe - Nom de l'équipe
     * @param flag      - flag de l'équipe
     * @return L'équipe
     */
    private Equipe recupererEquipe( Groupe groupe, String nomEquipe, String flag )
    {
        Equipe equipe = equipeRepository.findByNom( nomEquipe );
        if ( equipe == null )
        {
            equipe = new Equipe();
            equipe.setNom( nomEquipe );
            equipe.setFlag( flag );
            equipe.setGroupe( groupe );
            groupe.getEquipes().add( equipe );

            equipeRepository.save( equipe );
        }
        return equipe;
    }

    /**
     * Méthode de récupération ou création d'un groupe en BDD
     *
     * @param groupName - Nom du groupe
     * @return le groupe
     */
    private Groupe recupererGroupe( String groupName )
    {
        Groupe groupe;
        groupe = groupRepository.findByNom( groupName );
        if ( groupe == null )
        {
            groupe = new Groupe();
            groupe.setNom( groupName );
            groupRepository.save( groupe );
        }
        return groupe;
    }

    /**
     * Méthode de récupération ou création d'un matche en BDD
     *
     * @param dateMatch - date du match
     * @param ville     - ville du match
     * @param stade     - stade du match
     * @param equipe1   - equipe home
     * @param equipe2   - equipe away
     * @param groupe    - groupe du match
     * @return l'équipe
     * @throws ParseException - impossible de parser la date
     */
    private Matche recupererMatch( String dateMatch, String ville, String stade, Equipe equipe1, Equipe equipe2, Groupe groupe ) throws ParseException
    {
        Matche matche = matchRepository.findByEquipe1AndEquipe2( equipe1, equipe2 );
        logger.info("Match {}", matche);
        if ( matche == null )
        {
            Date date = m_ISO8601Local.parse( dateMatch );
            matche = new Matche();
            matche.setEquipe1( equipe1 );
            matche.setEquipe2( equipe2 );
            matche.setPhase( "Groupe" );
            matche.setDateMatch( date );
            matche.setStade( stade );
            matche.setVille( ville );
            matche.setGroupe( groupe );

            equipe1.getMatcheHomeList().add( matche );
            equipe2.getMatcheAwayList().add( matche );

            groupe.getMatches().add( matche );

            matchRepository.save( matche );
        }
        return matche;
    }
}
