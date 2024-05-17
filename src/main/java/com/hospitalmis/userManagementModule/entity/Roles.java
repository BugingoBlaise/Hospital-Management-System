package com.hospitalmis.userManagementModule.entity;

import lombok.*;
import javax.persistence.*;
import java.util.Set;
import java.util.UUID;


@NamedQuery(name = "Roles.searchRoleByName", query = "SELECT r FROM Roles r WHERE r.roleName LIKE :kw and r.isActive=true")
@NamedQuery(name = "Roles.listActiveRoles",query = "select r from Roles r where r.isActive=true")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles_table")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID roleId;
    private String roleName;
    @ManyToMany
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "roleId"),
            inverseJoinColumns = @JoinColumn(name = "permissionId"))
    private Set<Permissions> permissions;
    @ManyToMany(mappedBy = "roles")
    private Set<UserAccount>users;
    private boolean isActive=false;
}
