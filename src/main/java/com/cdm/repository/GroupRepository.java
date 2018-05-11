package com.cdm.repository;

import com.cdm.model.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Groupe, Long>
{
    /**
     * Méthode pour rechercher un groupe par nom
     *
     * @param name - nom à rechercher
     * @return le groupe
     */
    Groupe findByNom( String name );
}
