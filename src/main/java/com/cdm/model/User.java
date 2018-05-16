package com.cdm.model;

import com.cdm.controller.SignInController;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;

@Entity
@Table( name = "USERS" )
@JsonIgnoreProperties( { "hibernateLazyInitializer", "handler" } )
public class User
{

    /**
     * Logger
     */
    private static transient final Logger logger = LogManager.getLogger( User.class );

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "id_Sequence" )
    @SequenceGenerator( name = "id_Sequence", sequenceName = "USER_SEQ" )
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

    @JsonManagedReference( "classement-user" )
    @OneToOne( mappedBy = "user", targetEntity = Classement.class )
    private Classement classement;

    public int getId ()
    {
        return id;
    }

    public void setId ( int id )
    {
        this.id = id;
    }

    public String getUsername ()
    {
        return username;
    }

    public void setUsername ( String username )
    {
        this.username = username;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword ( String password )
    {
        this.password = password;
    }

    public String getMail ()
    {
        return mail;
    }

    public void setMail ( String email )
    {
        this.mail = email;
    }

    public Boolean getVerification ()
    {
        return verification;
    }

    public void setVerification ( Boolean verification )
    {
        this.verification = verification;
    }

    public String getToken ()
    {
        return token;
    }

    public void setToken ( String token )
    {
        this.token = token;
    }

    public Classement getClassement ()
    {
        return classement;
    }

    public void setClassement ( Classement classement )
    {
        this.classement = classement;
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
        User user = (User) o;
        return id == user.id &&
                Objects.equals( username, user.username ) &&
                Objects.equals( mail, user.mail ) &&
                Objects.equals( password, user.password ) &&
                Objects.equals( verification, user.verification ) &&
                Objects.equals( token, user.token );
    }

    @Override
    public int hashCode ()
    {
        return Objects.hash( id, username, mail, password, verification, token );
    }

    @Override
    public String toString ()
    {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", verification=" + verification +
                ", token='" + token + '\'' +
                '}';
    }


    /**
     * Génération d'un token pour l'api
     *
     * @return un token généré aléatoirement
     */
    public char[] generateToken ()
    {
        char[] digits = new char[16];
        SecureRandom srnd = null;
        try
        {
            srnd = SecureRandom.getInstance( "SHA1PRNG" );
        }
        catch ( NoSuchAlgorithmException e )
        {
            logger.error( "aucun algorithme connu", e );
            return digits;
        }
        for ( int i = 0 ; i < digits.length ; i++ )
        {
            digits[i] = (char) ( '0' + srnd.nextInt( 9 ) );
        }
        this.setToken( digits.toString() );
        return digits;
    }
}
