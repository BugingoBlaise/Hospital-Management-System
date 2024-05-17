package com.hospitalmis.userManagementModule.services;

import com.hospitalmis.userManagementModule.entity.Roles;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    public static String NAME = "RolesServiceImplementation";
    String save(Roles roles);
    void delete(Roles roles);
    void update(Roles roles);
    List<Roles> findAll();
    Roles findRoleByName(String role);
}
