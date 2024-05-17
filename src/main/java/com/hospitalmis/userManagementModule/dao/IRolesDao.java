package com.hospitalmis.userManagementModule.dao;

import com.hospitalmis.userManagementModule.entity.Roles;

import java.util.List;
import java.util.Optional;

public interface IRolesDao {
    Roles save(Roles role);
    List<Roles> queryAll();
    Optional<Roles>searchRoleByName(String keyword);

    Roles getRoleByName(String roles);
}
