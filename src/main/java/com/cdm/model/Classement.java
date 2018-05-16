package com.cdm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table( name = "CLASSEMENT" )
public class Classement
{
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "id_Sequence" )
    @SequenceGenerator( name = "id_Sequence", sequenceName = "CLASSEMENT_SEQ" )
    private int id;

    @JsonBackReference( value = "classement-user" )
    @OneToOne( targetEntity = User.class )
    @PrimaryKeyJoinColumn
    private User user;

    @Column( name = "NB_SCORE_EXACT", nullable = false )
    private Integer nbScoreExact = 0;

    @Column( name = "NB_EQUIPE_GAGNANTE", nullable = false )
    private Integer nbEquipeGagnante = 0;

    @Column( name = "POINTS", nullable = false )
    private Integer points = 0;

    public Classement ()
    {
    }

    public Classement ( int id, User user, Integer nbScoreExact, Integer nbEquipeGagnante, Integer points )
    {
        this.id = id;
        this.user = user;
        this.nbScoreExact = nbScoreExact;
        this.nbEquipeGagnante = nbEquipeGagnante;
        this.points = points;
    }

    public int getId ()
    {
        return id;
    }

    public void setId ( int id )
    {
        this.id = id;
    }

    public User getUser ()
    {
        return user;
    }

    public void setUser ( User user )
    {
        this.user = user;
    }

    public Integer getNbScoreExact ()
    {
        return nbScoreExact;
    }

    public void setNbScoreExact ( Integer nbScoreExact )
    {
        this.nbScoreExact = nbScoreExact;
    }

    public Integer getNbEquipeGagnante ()
    {
        return nbEquipeGagnante;
    }

    public void setNbEquipeGagnante ( Integer nbEquipeGagnante )
    {
        this.nbEquipeGagnante = nbEquipeGagnante;
    }

    public Integer getPoints ()
    {
        return points;
    }

    public void setPoints ( Integer points )
    {
        this.points = points;
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
        Classement that = (Classement) o;
        return id == that.id &&
                Objects.equals( user, that.user ) &&
                Objects.equals( nbScoreExact, that.nbScoreExact ) &&
                Objects.equals( nbEquipeGagnante, that.nbEquipeGagnante ) &&
                Objects.equals( points, that.points );
    }

    @Override
    public int hashCode ()
    {
        return Objects.hash( id, user, nbScoreExact, nbEquipeGagnante, points );
    }

    @Override
    public String toString ()
    {
        return "Classement{" +
                "id=" + id +
                ", user=" + user +
                ", nbScoreExact=" + nbScoreExact +
                ", nbEquipeGagnante=" + nbEquipeGagnante +
                ", points=" + points +
                '}';
    }

    public void calculPoints ()
    {
        this.points = 5 * nbScoreExact + 2 * getNbEquipeGagnante();
    }

    public void determinerScore ( List<Pronostic> pronostics )
    {
        int nbScoreExact = 0;
        int nbEquipeGagnante = 0;

        for ( Pronostic p : pronostics )
        {
            // Récupération du score du match
            Matche matche = p.getMatch();
            Integer scoreMatchHome = matche.getScoreEquipe1();
            Integer scoreMatchAway = matche.getScoreEquipe2();

            // Récupération du score du pronostic
            Integer scorePronoHome = p.getScoreHome();
            Integer scorePronoAway = p.getScoreAway();

            // Calcul des différences
            int diffScoreMatch = scoreMatchHome - scoreMatchAway;
            int diffScoreProno = scorePronoHome - scorePronoAway;

            if ( scoreMatchHome.equals( scorePronoHome ) && scoreMatchAway.equals( scorePronoAway ) )
            {
                nbScoreExact++;
            }
            if ( diffScoreMatch > 0 && diffScoreProno > 0 )
            {
                nbEquipeGagnante++;
            }
        }
        this.nbScoreExact = nbScoreExact;
        this.nbEquipeGagnante = nbEquipeGagnante;
    }
}
