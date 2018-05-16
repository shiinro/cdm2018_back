package com.cdm.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name = "EQUIPE" )
@JsonIgnoreProperties( { "hibernateLazyInitializer", "handler" } )
public class Equipe implements Serializable
{
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "id_Sequence" )
    @SequenceGenerator( name = "id_Sequence", sequenceName = "EQUIPE_SEQ" )
    private int id;

    @Column( nullable = false, name = "nom" )
    private String nom;

    @Column( name = "zone" )
    private String zone;

    @Column( name = "flag" )
    private String flag;

    @JsonBackReference(value="equipe-group")
    @ManyToOne( cascade = CascadeType.PERSIST, fetch = FetchType.LAZY )
    @JoinColumn( name = "GROUPE_ID" )
    private Groupe groupe;

    @JsonIgnore
    @OneToMany( mappedBy = "equipe1", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Matche> matcheHomeList = new ArrayList<>();

    @JsonIgnore
    @OneToMany( mappedBy = "equipe2", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Matche> matcheAwayList = new ArrayList<>();

    public int getId ()
    {
        return id;
    }

    public void setId ( int id )
    {
        this.id = id;
    }

    public String getNom ()
    {
        return nom;
    }

    public void setNom ( String nom )
    {
        this.nom = nom;
    }

    public String getZone ()
    {
        return zone;
    }

    public void setZone ( String zone )
    {
        this.zone = zone;
    }

    public String getFlag ()
    {
        return flag;
    }

    public void setFlag ( String flag )
    {
        this.flag = flag;
    }

    public Groupe getGroupe ()
    {
        return groupe;
    }

    public void setGroupe ( Groupe groupe )
    {
        this.groupe = groupe;
    }

    public List<Matche> getMatcheHomeList ()
    {
        if ( matcheHomeList == null )
        {
            matcheHomeList = new ArrayList<>();
        }
        return matcheHomeList;
    }

    public void setMatcheHomeList ( List<Matche> matcheHomeList )
    {
        this.matcheHomeList = matcheHomeList;
    }

    public List<Matche> getMatcheAwayList ()
    {
        if ( matcheAwayList == null )
        {
            matcheAwayList = new ArrayList<>();
        }
        return matcheAwayList;
    }

    public void setMatcheAwayList ( List<Matche> matcheAwayList )
    {
        this.matcheAwayList = matcheAwayList;
    }

    @Override
    public boolean equals ( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }
        Equipe equipe = (Equipe) o;
        return id == equipe.id &&
                Objects.equals( nom, equipe.nom ) &&
                Objects.equals( zone, equipe.zone ) &&
                Objects.equals( flag, equipe.flag );
    }

    @Override
    public int hashCode ()
    {

        return Objects.hash( id, nom, zone, flag );
    }
}
