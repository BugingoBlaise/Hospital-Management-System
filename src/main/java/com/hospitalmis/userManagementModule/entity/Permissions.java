package com.hospitalmis.userManagementModule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "permissions_table")
public class Permissions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID permissionId;
    private String permissionName;
    private String permissionDescription;
    @ManyToMany(mappedBy = "permissions")
    private Set<Roles> roles;
}
