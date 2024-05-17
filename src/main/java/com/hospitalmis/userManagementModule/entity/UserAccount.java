package com.hospitalmis.userManagementModule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@NamedQuery(
        name = "UserAccount.updateUserAccountPassword",
        query = "UPDATE UserAccount u SET u.password=:p"
)
@NamedQuery(name = "UserAccount.findByLastnameOrFirstname", query = "SELECT u FROM UserAccount u WHERE u.lastname like :name OR u.firstname like :name")
@NamedQuery(name = "UserAccount.searchUserByEmail", query = "SELECT u FROM UserAccount u WHERE u.email LIKE :kw")
@NamedQuery(name = "UserAccount.listActiveUsers", query = "SELECT u FROM UserAccount u WHERE u.isActive = false")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users_table")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private LocalDateTime lastLogin;
    private LocalDateTime accountExpiry = LocalDateTime.now().plusMinutes(30); // Set dynamically or adjust as needed
    private Boolean isActive = false;
    private Boolean passwordChanged = false;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles = new HashSet<>();

    public void setPasswordChanged(Boolean passwordChanged) {
        this.passwordChanged = passwordChanged;
        if (passwordChanged) {
            this.isActive = true;
        }
    }
}

