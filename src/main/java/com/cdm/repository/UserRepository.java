package com.cdm.repository;

import com.cdm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>
{
    /**
     * Méthode pour rechercher un user par name
     *
     * @param username - name à rechercher
     * @return Le user
     */
    User findByUsername( String username );

    /**
     * Méthode pour rechercher un user par email
     *
     * @param mail - email à rechercher
     * @return Le user
     */
    Optional<User> findByMail( String mail );

    /**
     * Récupération des points
     * @return
     */
    @Query("select u from User u join u.classement c order by c.points desc")
    List<User> findClassement();
}
