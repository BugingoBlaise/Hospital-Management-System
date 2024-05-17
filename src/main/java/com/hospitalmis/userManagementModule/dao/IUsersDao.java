package com.hospitalmis.userManagementModule.dao;



import com.hospitalmis.userManagementModule.entity.UserAccount;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUsersDao {
    List<UserAccount> queryAll();
    void save(UserAccount user);
    UserAccount update(UserAccount userAccount);
    Optional<UserAccount>findByLastnameOrFirstname(String keyword);
    Optional<UserAccount> getUsersById(UserAccount userAccount);
   void delete(UserAccount user);
     Optional<UserAccount> searchUserByName(String keyword);
     UserAccount getAccount(UUID account);
     void updateUserAccountPassword(String password);
}
