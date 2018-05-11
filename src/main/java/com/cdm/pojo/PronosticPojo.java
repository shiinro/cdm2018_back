package com.cdm.pojo;

import com.cdm.model.Equipe;
import com.cdm.model.Matche;
import com.cdm.model.Pronostic;
import com.cdm.model.User;

import java.util.Date;
import java.util.Objects;

public class PronosticPojo
{
    private Integer id;

    private Date dateMatch;

    private User user;

    private Equipe equipeHome;

    private Equipe equipeAway;

    private Integer scoreEquipeHome;

    private Integer scoreEquipeAway;

    public PronosticPojo( int i, Matche match, Pronostic pronostic )
    {
        this.id = i;
        this.dateMatch = match.getDateMatch();
        this.equipeHome = match.getEquipe1();
        this.equipeAway = match.getEquipe2();
        this.scoreEquipeHome = pronostic == null ? 0 : pronostic.getScoreHome();
        this.scoreEquipeAway = pronostic == null ? 0 : pronostic.getScoreAway();
        this.user = null;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public Date getDateMatch()
    {
        return dateMatch;
    }

    public void setDateMatch( Date dateMatch )
    {
        this.dateMatch = dateMatch;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser( User user )
    {
        this.user = user;
    }

    public Equipe getEquipeHome()
    {
        return equipeHome;
    }

    public void setEquipeHome( Equipe equipeHome )
    {
        this.equipeHome = equipeHome;
    }

    public Equipe getEquipeAway()
    {
        return equipeAway;
    }

    public void setEquipeAway( Equipe equipeAway )
    {
        this.equipeAway = equipeAway;
    }

    public Integer getScoreEquipeHome()
    {
        return scoreEquipeHome;
    }

    public void setScoreEquipeHome( Integer scoreEquipeHome )
    {
        this.scoreEquipeHome = scoreEquipeHome;
    }

    public Integer getScoreEquipeAway()
    {
        return scoreEquipeAway;
    }

    public void setScoreEquipeAway( Integer scoreEquipeAway )
    {
        this.scoreEquipeAway = scoreEquipeAway;
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
        PronosticPojo pojo = ( PronosticPojo ) o;
        return Objects.equals( id, pojo.id ) &&
                Objects.equals( dateMatch, pojo.dateMatch ) &&
                Objects.equals( user, pojo.user ) &&
                Objects.equals( equipeHome, pojo.equipeHome ) &&
                Objects.equals( equipeAway, pojo.equipeAway ) &&
                Objects.equals( scoreEquipeHome, pojo.scoreEquipeHome ) &&
                Objects.equals( scoreEquipeAway, pojo.scoreEquipeAway );
    }

    @Override
    public int hashCode()
    {

        return Objects.hash( id, dateMatch, user, equipeHome, equipeAway, scoreEquipeHome, scoreEquipeAway );
    }

    @Override
    public String toString()
    {
        return "PronosticPojo{" +
                "id=" + id +
                ", dateMatch=" + dateMatch +
                ", user=" + user +
                ", equipeHome=" + equipeHome +
                ", equipeAway=" + equipeAway +
                ", scoreEquipeHome=" + scoreEquipeHome +
                ", scoreEquipeAway=" + scoreEquipeAway +
                '}';
    }
}
