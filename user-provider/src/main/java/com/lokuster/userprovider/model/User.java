package com.lokuster.userprovider.model;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name = "getUserByUsername", query = """
                select u
                from User u
                where u.username = :username
                """),
        @NamedQuery(name = "getUserByEmail", query = """
                select u from
                User u
                where u.email = :email
                """),
        @NamedQuery(name = "getUserCount", query = """
                select count(u)
                from User u
                """),
        @NamedQuery(name = "getAllUsers", query = """
                select u
                from User u
                """),
        @NamedQuery(name = "searchForUser", query = """
                select u
                from User u
                where (lower(u.username) like :search or u.email like :search)
                order by u.username
                """),
        @NamedQuery(name = "getUserPasswordById", query = """
                select u.password
                from User u
                where u.id = :id
                """)
})
@Getter
@Setter
@Table(name = "users")
@Entity
public class User {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "username", nullable = false)
    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @Column(name = "email", nullable = false)
    @NotBlank
    @Email
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    private String password;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @Column(name = "active", columnDefinition = "bool default true")
    private Boolean active;

    @Column(name = "created", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    private Date createDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}
