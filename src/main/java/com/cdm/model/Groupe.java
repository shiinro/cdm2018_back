package com.cdm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name = "Groupe" )
@JsonIgnoreProperties( { "hibernateLazyInitializer", "handler" } )
public class Groupe implements Serializable
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @Column( name = "nom" )
    private String nom;

    @JsonBackReference
    @OneToMany( targetEntity = Equipe.class, mappedBy = "groupe", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY )
    private List<Equipe> equipes = new ArrayList<>();

    @JsonBackReference
    @OneToMany( targetEntity = Matche.class, mappedBy = "groupe", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY )
    private List<Matche> matches = new ArrayList<>();

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getNom()
    {
        return nom;
    }

    public void setNom( String nom )
    {
        this.nom = nom;
    }

    public List<Equipe> getEquipes()
    {
        if ( equipes == null )
        {
            equipes = new ArrayList<>();
        }
        return equipes;
    }

    public void setEquipes( List<Equipe> equipes )
    {
        this.equipes = equipes;
    }

    public List<Matche> getMatches()
    {
        if ( matches == null )
        {
            matches = new ArrayList<>();
        }
        return matches;
    }

    public void setMatches( List<Matche> matches )
    {
        this.matches = matches;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }
        Groupe groupe = ( Groupe ) o;
        return id == groupe.id &&
                Objects.equals( nom, groupe.nom );
    }

    @Override
    public int hashCode()
    {

        return Objects.hash( id, nom );
    }
}
