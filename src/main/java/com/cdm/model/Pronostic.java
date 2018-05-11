package com.cdm.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table( name = "PRONOSTIC" )
public class Pronostic implements Serializable
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @JsonManagedReference
    @ManyToOne( cascade = CascadeType.PERSIST, fetch = FetchType.LAZY )
    @JoinColumn( name = "user_id" )
    private User user;

    @JsonManagedReference
    @ManyToOne( cascade = CascadeType.PERSIST, fetch = FetchType.LAZY )
    @JoinColumn( name = "match_id" )
    private Matche match;

    @Column( name = "score_home" )
    private Integer scoreHome;

    @Column( name = "score_away" )
    private Integer scoreAway;

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser( User user )
    {
        this.user = user;
    }

    public Matche getMatch()
    {
        return match;
    }

    public void setMatch( Matche match )
    {
        this.match = match;
    }

    public Integer getScoreHome()
    {
        return scoreHome;
    }

    public void setScoreHome( Integer scoreHome )
    {
        this.scoreHome = scoreHome;
    }

    public Integer getScoreAway()
    {
        return scoreAway;
    }

    public void setScoreAway( Integer scoreAway )
    {
        this.scoreAway = scoreAway;
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
        Pronostic pronostic = ( Pronostic ) o;
        return id == pronostic.id &&
                Objects.equals( user, pronostic.user ) &&
                Objects.equals( match, pronostic.match ) &&
                Objects.equals( scoreHome, pronostic.scoreHome ) &&
                Objects.equals( scoreAway, pronostic.scoreAway );
    }

    @Override
    public int hashCode()
    {

        return Objects.hash( id, user, match, scoreHome, scoreAway );
    }

    @Override
    public String toString()
    {
        return "Pronostic{" +
                "id=" + id +
                ", user=" + user +
                ", match=" + match +
                ", scoreHome=" + scoreHome +
                ", scoreAway=" + scoreAway +
                '}';
    }
}
