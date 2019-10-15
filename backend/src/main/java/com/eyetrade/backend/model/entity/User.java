package com.eyetrade.backend.model.entity;

import com.eyetrade.backend.constants.MessageTypeConstants;
import com.eyetrade.backend.constants.Role;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", length = ID_LENGTH)
    private String identifier;

    @NotNull
    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "pword")
    @NotEmpty(message = "Please provide your password")
    private String pword;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String phone;

    @NotNull
    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private String iban;

    @Column
    private String identityNo;

    @Column
    private String city;

    @Column
    private String locationX;

    @Column
    private String locationY;

    @Column(name = "confirmed")
    private boolean confirmed;
}
