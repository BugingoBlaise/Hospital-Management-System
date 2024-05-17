package com.hospitalmis.userManagementModule.viewmodels;

 import com.hospitalmis.commons.services.AuthenticationService;
import com.hospitalmis.userManagementModule.entity.RequestChangePassword;
import com.hospitalmis.userManagementModule.entity.Roles;
import com.hospitalmis.userManagementModule.entity.UserAccount;
import com.hospitalmis.userManagementModule.services.IRoleService;
import com.hospitalmis.userManagementModule.services.IUserInfoService;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;

 import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class UsersViewModel {
    @WireVariable(IUserInfoService.NAME)
    IUserInfoService iUserInfoService;
    @Setter
    @Getter
    UserAccount userAccount;
    @WireVariable(IRoleService.NAME)
    IRoleService iRoleService;
    @Getter
    @Setter
    private Roles selectedRole;
    @Getter
    @Setter
    private RequestChangePassword requestChangePassword;
    @WireVariable(IUserInfoService.NAME)
    AuthenticationService authService;
    @Getter
    @Setter
    private ListModelList<Roles> rolesListModelList;

    @Init
    public void init() {
        this.userAccount = new UserAccount();
        List<Roles> rolesList = iRoleService.findAll();
        rolesListModelList = new ListModelList<Roles>(rolesList);
    }


    @Command
    public void createUser() {
        String message = iUserInfoService.save(this.userAccount, this.selectedRole);
        Clients.showNotification(message);
    }

    @Command
    public void changePassword() {
        try {

//            iUserInfoService.changePassword();
//            Clients.showNotification(result);

        } catch (Exception e) {
            // Handle exception (e.g., show error message)
            Clients.showNotification("Error: " + e.getMessage());
        }
    }
    private void loadRoles() {
        List<Roles> roles = iRoleService.findAll();
        rolesListModelList = new ListModelList<>(roles);
    }

}
