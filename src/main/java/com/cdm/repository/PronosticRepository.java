package com.cdm.repository;

import com.cdm.model.Pronostic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PronosticRepository extends JpaRepository<Pronostic, Integer>
{
    /**
     * Méthode pour récuperer tous les pronostics d'un utilisateur
     *
     * @param user - id de l'utilisateur
     * @return
     */
    @Query( "select p from Pronostic p where p.user.id = ?1" )
    List<Pronostic> findByUserId ( Integer user );

    @Query("select p from Pronostic p where p.user.id = ?1 and p.match.equipe1.nom = ?2 and p.match.equipe2.nom = ?3")
    Pronostic findByUserIdAndMatch(Integer user, String nomEquipeHome, String nomEquipeAway);
}
