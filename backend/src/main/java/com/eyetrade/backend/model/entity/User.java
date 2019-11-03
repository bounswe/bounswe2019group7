package com.eyetrade.backend.model.entity;

import com.eyetrade.backend.constants.Role;
import com.eyetrade.backend.constants.PrivacyType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.UUID;

import static com.eyetrade.backend.constants.GeneralConstants.ID_LENGTH;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

@Data
@Entity
@Table(name="users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", length = ID_LENGTH)
    private UUID id;

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

    @Column(name = "identity_no")
    private String identityNo;

    @Column(name = "city")
    private String city;

    @Column(name = "location_x")
    private String locationX;

    @Column(name = "location_y")
    private String locationY;

    @Column(name = "confirmed")
    private boolean confirmed;

    @NotNull
    @Column(name = "privacy_type")
    @Enumerated(EnumType.STRING)
    private PrivacyType privacyType;
}