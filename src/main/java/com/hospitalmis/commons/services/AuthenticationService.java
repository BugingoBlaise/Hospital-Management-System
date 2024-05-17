package com.hospitalmis.commons.services;

import com.hospitalmis.commons.Entity.UserCredential;

public interface AuthenticationService {
    public static final String NAME = "AuthenticationServiceImpl";
     public boolean login(String account, String password);
     public void logout();

     public UserCredential getUserCredential();
}
