package com.hospitalmis.userManagementModule.services.Implementation;

import com.hospitalmis.userManagementModule.dao.IRolesDao;
import com.hospitalmis.userManagementModule.entity.Roles;
import com.hospitalmis.userManagementModule.services.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Slf4j
@Service(IRoleService.NAME)
public class RoleServiceImpl implements IRoleService {
    @Autowired
    IRolesDao iRolesDao;

    @Override
    public String save(Roles roles) {
        try{
            roles.setRoleName(roles.getRoleName());
            roles.setActive(true);
           iRolesDao.save(roles);
           return "ROLE SAVED";
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
       return "ERROR";
    }
    @Override
    public Roles findRoleByName(String role) {
        return iRolesDao.getRoleByName(role);
    }


    @Override
    public void delete(Roles roles) {

    }

    @Override
    public void update(Roles roles) {

    }

    @Override
    public List<Roles> findAll() {
        return iRolesDao.queryAll();
    }


}
