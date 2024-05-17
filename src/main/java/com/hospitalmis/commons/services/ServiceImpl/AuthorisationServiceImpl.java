package com.hospitalmis.commons.services.ServiceImpl;

import com.hospitalmis.commons.Entity.UserCredential;
import com.hospitalmis.commons.services.AuthenticationService;
import com.hospitalmis.commons.services.AuthorisationService;
import com.hospitalmis.userManagementModule.entity.Roles;
import com.hospitalmis.userManagementModule.entity.UserAccount;
import com.hospitalmis.userManagementModule.services.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;


import java.io.Serializable;
import java.util.Set;

@Service(AuthorisationService.NAME)
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AuthorisationServiceImpl implements AuthorisationService, Serializable {
    AuthenticationService authService;
    IUserInfoService userInfoService;

    @Autowired
    public AuthorisationServiceImpl(AuthenticationService authService, IUserInfoService iUserInfoService) {
        this.authService = authService;
        this.userInfoService = iUserInfoService;
    }

    @Override
    public String redirect() {
        Session sess = Sessions.getCurrent();
        UserCredential userCredential = (UserCredential) sess.getAttribute("userCredential");
        Set<Roles> r = userCredential.getRole();

        UserAccount userAccount = userInfoService.findUserByEmail(userCredential.getEmail());

        String page = "default.zul";

        System.out.println(userCredential.getEmail() + " " + userCredential.getRole().toString().trim());


        if (r.stream().anyMatch(role -> role.getRoleName().equals("ROLE_ADMIN"))) {
            page = "adminPage.zul";
        } else if (r.stream().anyMatch(role -> role.getRoleName().equals("NURSE"))) {
            page = "nurse.zul";
        } else if (r.stream().anyMatch(roles -> roles.getRoleName().equals("USER"))) {
            if (userAccount.getIsActive()) {

                page = "default.zul";
            } else {
                page = "changePassword.zul";
            }
        }
        return page;
    }
}
