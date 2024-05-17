package com.hospitalmis.commons.viewModels;

import com.hospitalmis.commons.Entity.UserCredential;
import com.hospitalmis.commons.services.AuthenticationService;
import com.hospitalmis.userManagementModule.entity.Roles;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Initiator;

import java.util.Map;
import java.util.Set;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AuthenticationInit implements Initiator {
    @WireVariable(AuthenticationService.NAME)
    AuthenticationService authService;
    @Override
    public void doInit(Page page, Map<String, Object> args) throws Exception {
        Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
        Session sess = Sessions.getCurrent();
        UserCredential userCredential = (UserCredential) sess.getAttribute("userCredential");

        if(userCredential==null ){
            Executions.sendRedirect("/login.zul");
            return;
        }
    }
}
