package com.hospitalmis.commons.viewModels;

import com.hospitalmis.commons.Entity.UserCredential;
import com.hospitalmis.commons.services.AuthenticationService;
import com.hospitalmis.commons.services.AuthorisationService;
import com.hospitalmis.userManagementModule.entity.UserAccount;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.Notification;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class LoginViewModel {

    @WireVariable(AuthenticationService.NAME)
    AuthenticationService authService;
    @WireVariable(AuthorisationService.NAME)
    AuthorisationService authorisationService;
    @Getter
    @Setter
    private UserAccount account;
    @Setter
    @Getter
    private String email;
    @Setter
    @Getter
    private String password;

    @Init
    public void init() {
        System.out.println("------reached-------");
    }

    @Command
    public void login() {
        if (!authService.login(email, password)) {

            Notification.show("INCORRECT CREDENTIALS");
            return;
        }
        UserCredential userCredential = authService.getUserCredential();
        System.out.println(userCredential.getEmail() + " " + userCredential.getRole().toString().trim());
        String url=authorisationService.redirect();
        Executions.sendRedirect(url);
    }

}
