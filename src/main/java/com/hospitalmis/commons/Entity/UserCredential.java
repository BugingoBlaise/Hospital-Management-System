package com.hospitalmis.commons.Entity;

import com.hospitalmis.userManagementModule.entity.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;


public class UserCredential implements Serializable {
    private static final long serialVersionUID = 1L;

    String email;
    String names;
    Set<Roles> role;

    public UserCredential() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public Set<Roles> getRole() {
        return role;
    }

    public void setRole(Set<Roles> role) {
        this.role = role;
    }
}
