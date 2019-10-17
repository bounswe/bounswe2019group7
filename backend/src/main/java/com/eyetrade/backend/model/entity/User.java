package com.eyetrade.backend.model.entity;

import com.eyetrade.backend.constants.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

@Getter
@Setter
@Entity
@Table(name="users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "pword")
    @NotEmpty(message = "Please provide your password")
    private String pword;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "phone")
    private String phone;

    @NotNull
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "iban")
    private String iban;

    @Column(name = "identityNo")
    private String identityNo;

    @Column(name = "city")
    private String city;

    @Column(name = "locationX")
    private String locationX;

    @Column(name = "locationY")
    private String locationY;

    @Column(name = "confirmed")
    private boolean confirmed;

}
