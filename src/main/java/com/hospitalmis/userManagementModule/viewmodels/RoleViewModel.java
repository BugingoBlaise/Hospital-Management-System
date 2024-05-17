package com.hospitalmis.userManagementModule.viewmodels;

import com.hospitalmis.userManagementModule.entity.Roles;
import com.hospitalmis.userManagementModule.services.IRoleService;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModelSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class RoleViewModel {
    @WireVariable(IRoleService.NAME)
    IRoleService iRoleService;
    @Getter
    @Setter
    private Roles roles;
    @Getter
    @Setter
    private Roles selectedRole;
    @Getter
    @Setter
    private List<Roles> allRoles = new ArrayList<>();


    @Init
    public void init() {
        this.roles = new Roles();
        this.allRoles = iRoleService.findAll();
    }
    @Command
    @NotifyChange("allRoles")
    public void createRole() {
        String re = iRoleService.save(this.roles);
        Clients.showNotification(re);
    }

}
