package com.hospitalmis.userManagementModule.services;

import com.hospitalmis.userManagementModule.entity.RequestChangePassword;
import com.hospitalmis.userManagementModule.entity.Roles;
import com.hospitalmis.userManagementModule.entity.UserAccount;

import java.util.List;
import java.util.UUID;


public interface IUserInfoService {
    public static String NAME = "UserInfoServiceImplementation";
    String save(UserAccount userAccount,Roles rolename);
    void delete(UserAccount userAccount);
    void update(UserAccount userAccount);
    List<Roles> findAll();
    UserAccount findUserByEmail(String email);
    boolean isTruePassword(UUID userAccountId, String userPassword)throws Exception;
    int changeUserPassword(UserAccount userAccount,String newPassword)throws Exception;
    String changePassword(RequestChangePassword request) throws Exception;
}
