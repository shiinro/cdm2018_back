package com.cdm.pojo;

import java.util.Objects;

public class ClassementPojo
{
    private String nomUser;

    private Integer nbScoreExact = 0;

    private Integer nbEquipeGagnante = 0;

    private Integer points = 0;

    public ClassementPojo ()
    {
    }

    public ClassementPojo ( String nomUser, Integer nbScoreExact, Integer nbEquipeGagnante, Integer points )
    {
        this.nomUser = nomUser;
        this.nbScoreExact = nbScoreExact;
        this.nbEquipeGagnante = nbEquipeGagnante;
        this.points = points;
    }

    public String getNomUser ()
    {
        return nomUser;
    }

    public void setNomUser ( String nomUser )
    {
        this.nomUser = nomUser;
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
        ClassementPojo that = (ClassementPojo) o;
        return Objects.equals( nomUser, that.nomUser ) &&
                Objects.equals( nbScoreExact, that.nbScoreExact ) &&
                Objects.equals( nbEquipeGagnante, that.nbEquipeGagnante ) &&
                Objects.equals( points, that.points );
    }

    @Override
    public int hashCode ()
    {

        return Objects.hash( nomUser, nbScoreExact, nbEquipeGagnante, points );
    }

    @Override
    public String toString ()
    {
        return "ClassementPojo{" +
                "nomUser='" + nomUser + '\'' +
                ", nbScoreExact=" + nbScoreExact +
                ", nbEquipeGagnante=" + nbEquipeGagnante +
                ", points=" + points +
                '}';
    }
}
