package com.cdm.pojo;

import com.cdm.model.User;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PronosticRequestBody
{
    private User user;

    Map<String, List<PronosticPojo>> pronostics;

    public PronosticRequestBody ()
    {
    }

    public PronosticRequestBody ( User user, Map<String, List<PronosticPojo>> pronostics )
    {
        this.user = user;
        this.pronostics = pronostics;
    }

    public User getUser ()
    {
        return user;
    }

    public void setUser ( User user )
    {
        this.user = user;
    }

    public Map<String, List<PronosticPojo>> getPronostics ()
    {
        return pronostics;
    }

    public void setPronostics ( Map<String, List<PronosticPojo>> pronostics )
    {
        this.pronostics = pronostics;
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
        PronosticRequestBody that = (PronosticRequestBody) o;
        return Objects.equals( user, that.user ) &&
                Objects.equals( pronostics, that.pronostics );
    }

    @Override
    public int hashCode ()
    {

        return Objects.hash( user, pronostics );
    }

    @Override
    public String toString ()
    {
        return "PronosticRequestBody{" +
                "user=" + user +
                ", pronostics=" + pronostics +
                '}';
    }
}
