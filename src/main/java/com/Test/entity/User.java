package com.Test.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="users",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String username;
    private String email;
    private String password;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name="user_roles",
       joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),
       inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "id")
    )
    private Set<Role> roles;
}
