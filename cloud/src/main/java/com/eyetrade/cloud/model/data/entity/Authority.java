package com.eyetrade.cloud.model.data.entity;

import com.eyetrade.cloud.util.constants.Role;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
        import javax.validation.constraints.NotEmpty;

        import static com.eyetrade.cloud.util.constants.GeneralConstants.ID_LENGTH;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */
@Data
@Entity
@Table(name="authority")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", length = ID_LENGTH)
    private String identifier;

    @Column(name = "email")
    @NotEmpty(message = "Please provide your email")
    private String email;

    @Column(name = "role")
    private Role role;


    public Authority(String email, Role role){
        this.role = role;
        this.email = email;
    }

}
