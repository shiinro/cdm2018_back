package com.cdm.repository;

import com.cdm.model.Pronostic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PronosticRepository extends JpaRepository<Pronostic, Integer>
{
    @Query( "select p from Pronostic p where p.user.id = ?1" )
    public List<Pronostic> findByUserId( Integer user );
}
