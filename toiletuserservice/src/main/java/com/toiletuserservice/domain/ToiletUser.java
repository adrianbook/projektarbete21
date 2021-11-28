package com.toiletuserservice.domain;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Class containing user information
 * Is a JPA entity
 * written by JASB
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToiletUser {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @Column(unique=true)
    private String username;
    private String password;
    @Column(unique=true)
    private String email;
    @ManyToMany(fetch = FetchType.EAGER )
    private Collection<Role> roles = new ArrayList<>();

}