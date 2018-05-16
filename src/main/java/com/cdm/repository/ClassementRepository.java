package com.cdm.repository;

import com.cdm.model.Classement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassementRepository extends JpaRepository<Classement, Integer>
{
    /**
     * Recherche d'un classement par user
     *
     * @param user - User à rechercher
     * @return Classement du user
     */
    Classement findByUser ( String user );
}
