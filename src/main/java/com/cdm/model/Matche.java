package com.cdm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table( name = "MATCHE" )
@JsonIgnoreProperties( { "hibernateLazyInitializer", "handler" } )
public class Matche implements Serializable
{
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "id_Sequence" )
    @SequenceGenerator( name = "id_Sequence", sequenceName = "MATCHE_SEQ" )
    private int id;

    @ManyToOne( cascade = CascadeType.PERSIST, fetch = FetchType.LAZY )
    @JoinColumn( name = "EQUIPE_1_ID" )
    private Equipe equipe1;

    @ManyToOne( cascade = CascadeType.PERSIST, fetch = FetchType.LAZY )
    @JoinColumn( name = "EQUIPE_2_ID" )
    private Equipe equipe2;

    @JsonBackReference( value = "group-match" )
    @ManyToOne( cascade = CascadeType.PERSIST, fetch = FetchType.LAZY )
    @JoinColumn( name = "GROUPE_ID" )
    private Groupe groupe;

    @Column
    private Integer scoreEquipe1;

    @Column
    private Integer scoreEquipe2;

    @Column
    @Temporal( TemporalType.DATE )
    private Date dateMatch;

    @Column
    private String phase;

    @Column
    private String ville;

    @Column
    private String stade;

    public int getId ()
    {
        return id;
    }

    public void setId ( int id )
    {
        this.id = id;
    }

    public Equipe getEquipe1 ()
    {
        return equipe1;
    }

    public void setEquipe1 ( Equipe equipe1 )
    {
        this.equipe1 = equipe1;
    }

    public Equipe getEquipe2 ()
    {
        return equipe2;
    }

    public void setEquipe2 ( Equipe equipe2 )
    {
        this.equipe2 = equipe2;
    }

    public Groupe getGroupe ()
    {
        return groupe;
    }

    public void setGroupe ( Groupe groupe )
    {
        this.groupe = groupe;
    }

    public Integer getScoreEquipe1 ()
    {
        return scoreEquipe1;
    }

    public void setScoreEquipe1 ( Integer scoreEquipe1 )
    {
        this.scoreEquipe1 = scoreEquipe1;
    }

    public Integer getScoreEquipe2 ()
    {
        return scoreEquipe2;
    }

    public void setScoreEquipe2 ( Integer scoreEquipe2 )
    {
        this.scoreEquipe2 = scoreEquipe2;
    }

    public Date getDateMatch ()
    {
        return dateMatch;
    }

    public void setDateMatch ( Date dateMatch )
    {
        this.dateMatch = dateMatch;
    }

    public String getPhase ()
    {
        return phase;
    }

    public void setPhase ( String phase )
    {
        this.phase = phase;
    }

    public String getVille ()
    {
        return ville;
    }

    public void setVille ( String ville )
    {
        this.ville = ville;
    }

    public String getStade ()
    {
        return stade;
    }

    public void setStade ( String stade )
    {
        this.stade = stade;
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
        Matche matche = (Matche) o;
        return id == matche.id &&
                Objects.equals( equipe1, matche.equipe1 ) &&
                Objects.equals( equipe2, matche.equipe2 ) &&
                Objects.equals( scoreEquipe1, matche.scoreEquipe1 ) &&
                Objects.equals( scoreEquipe2, matche.scoreEquipe2 ) &&
                Objects.equals( dateMatch, matche.dateMatch ) &&
                Objects.equals( phase, matche.phase );
    }

    @Override
    public int hashCode ()
    {

        return Objects.hash( id, equipe1, equipe2, scoreEquipe1, scoreEquipe2, dateMatch, phase );
    }
}
