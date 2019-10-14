package com.eyetrade.cloud.model.data.entity;

import com.eyetrade.cloud.util.constants.Role;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static com.eyetrade.cloud.util.constants.GeneralConstants.ID_LENGTH;

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

    @Column(name = "password")
    @NotEmpty(message = "Please provide your password")
    private String password;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String phone;

    @NotNull
    @Column
    private String userType;

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
