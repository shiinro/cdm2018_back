package com.cdm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table( name = "USERS" )
@JsonIgnoreProperties( { "hibernateLazyInitializer", "handler" } )
public class User
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @Column( name = "username" )
    private String username;

    @Column( name = "mail" )
    private String mail;

    @Column( name = "password" )
    private String password;

    @Column( name = "verification" )
    private Boolean verification;

    @Transient
    private String token;

    @Column( name = "points", columnDefinition = "int default 0")
    private Integer points;

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername( String username )
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public String getMail()
    {
        return mail;
    }

    public void setMail( String email )
    {
        this.mail = email;
    }

    public Boolean getVerification()
    {
        return verification;
    }

    public void setVerification( Boolean verification )
    {
        this.verification = verification;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken( String token )
    {
        this.token = token;
    }

    public Integer getPoints()
    {
        return points;
    }

    public void setPoints( Integer points )
    {
        this.points = points;
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
        User user = ( User ) o;
        return id == user.id &&
                Objects.equals( username, user.username ) &&
                Objects.equals( mail, user.mail ) &&
                Objects.equals( password, user.password ) &&
                Objects.equals( verification, user.verification ) &&
                Objects.equals( token, user.token ) &&
                Objects.equals( points, user.points );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( id, username, mail, password, verification, token, points );
    }

    @Override
    public String toString()
    {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", verification=" + verification +
                ", token='" + token + '\'' +
                ", points=" + points +
                '}';
    }
}
