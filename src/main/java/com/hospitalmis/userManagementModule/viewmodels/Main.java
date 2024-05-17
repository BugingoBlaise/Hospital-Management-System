package com.hospitalmis.userManagementModule.viewmodels;

import com.hospitalmis.userManagementModule.entity.Roles;
import com.hospitalmis.userManagementModule.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Main {
    /*@Autowired
    private IRoleService iRoleService;

    public static void main(String[] args) {
        // Initialize the Spring application context
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Retrieve the bean from the application context
        IRoleService main = context.getBean(IRoleService.class);

        // Call a method to demonstrate usage
        main.save();
    }

    public void createRole() {
        // Create a new role
        Roles role = new Roles();
        role.setRoleName("ROLE_ADMIN");

        // Save the role using the injected service
        iRoleService.save(role);
    }

     */

}
