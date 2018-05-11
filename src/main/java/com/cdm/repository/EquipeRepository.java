package com.cdm.repository;

import com.cdm.model.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long>
{
    /**
     * Récupération d'une équipe par son nom
     *
     * @param nom - Nom de l'équipe
     * @return L'équipe
     */
    Equipe findByNom( String nom );
}
