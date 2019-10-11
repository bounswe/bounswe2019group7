package com.traders.backend.models.domain_models;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="User")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    @Email
    @NotNull
    @UniqueElements
    public String email; // email is the username

    @NotNull
    public String password;

    @NotNull
    public String name;

    @NotNull
    public String surname;

    @NotNull
    public Double locationX;

    @NotNull
    public Double locationY;

    @NotNull
    public String role; // might be trader or basic

    @NotNull
    public Boolean isPublic;

    @NotNull
    public Boolean isActive; // whether the user has activated her account via email

    public User(String email, String name, String surname, Double locationX, Double locationY, String role){
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.locationX = locationX;
        this.locationY = locationY;
        this.role = role;
        this.isPublic = true; // the accounts are public by default
        this.isActive = false; // when the account is created, it is not active yet
    }

    public void activate(){ // activates the account
        this.isActive = true;
    }

}
