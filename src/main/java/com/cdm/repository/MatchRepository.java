package com.cdm.repository;

import com.cdm.model.Equipe;
import com.cdm.model.Matche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface MatchRepository extends JpaRepository<Matche, Long>
{
    /**
     * Méthode pour rechercher un match par l'équipe home et l'équipe away
     *
     * @param equipe1 - équipe home
     * @param equipe2 - équipe away
     * @return - le match
     */
    Matche findByEquipe1AndEquipe2( Equipe equipe1, Equipe equipe2 );

    /**
     * Méthode pour rechercher tous les matchs d'un groupe
     *
     * @param groupeId - groupe des matches
     * @return - liste des matches
     */
    @Query( "select m from Matche m where m.groupe.id = ?1" )
    List<Matche> findByGroupeId( int groupeId );

    @Query("select m from Matche m group by m.dateMatch")
    Map<Date, List<Matche>> findAllMatchGroupByDate();
}
