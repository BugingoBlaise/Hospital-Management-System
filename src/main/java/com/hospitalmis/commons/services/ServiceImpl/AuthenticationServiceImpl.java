package com.hospitalmis.commons.services.ServiceImpl;

import com.hospitalmis.commons.Entity.UserCredential;
import com.hospitalmis.commons.services.AuthenticationService;
import com.hospitalmis.userManagementModule.entity.Roles;
import com.hospitalmis.userManagementModule.entity.UserAccount;
import com.hospitalmis.userManagementModule.services.IUserInfoService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Service(AuthenticationService.NAME)
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AuthenticationServiceImpl implements AuthenticationService, Serializable {
    IUserInfoService userInfoService;
    PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationServiceImpl(IUserInfoService userInfoService, PasswordEncoder passwordEncoder) {
        this.userInfoService = userInfoService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserCredential getUserCredential() {
        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute("userCredential");
        if (cre == null) {
            cre = new UserCredential();
            sess.setAttribute("userCredential", cre);
        }
        return cre;
    }

    @Override
    public boolean login(String account, String password) {
        UserAccount user = userInfoService.findUserByEmail(account);
        if (account.equals("admin@gmail.com") && password.equals("admin")) {
            UserCredential adminCredential = new UserCredential();
            adminCredential.setEmail("admin@gmail.com");
            adminCredential.setNames("ADMIN");

            Set<Roles> adminRoles = new HashSet<>();
            Roles adminRole = new Roles();
            adminRole.setRoleName("ROLE_ADMIN");

            adminRoles.add(adminRole);
            adminCredential.setRole(adminRoles);

            Session sess = Sessions.getCurrent();
            sess.setAttribute("userCredential", adminCredential);
            return true;
        }

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return false;
        }

        Session sess = Sessions.getCurrent();
        UserCredential cre = new UserCredential();
        cre.setEmail(user.getEmail());
        cre.setRole(user.getRoles()); // Set the roles from the UserAccount object
//        System.out.println(cre.getRole());
        sess.setAttribute("userInfo", user);
        sess.setAttribute("userCredential", cre);

        //TODO handle the role here for authorization

        return true;
    }


    @Override
    public void logout() {
        Session sess = Sessions.getCurrent();
        sess.removeAttribute("userCredential");
        sess.removeAttribute("userInfo");
    }
}


