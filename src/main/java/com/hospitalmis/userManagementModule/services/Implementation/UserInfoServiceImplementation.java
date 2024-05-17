package com.hospitalmis.userManagementModule.services.Implementation;

import com.hospitalmis.userManagementModule.dao.IUsersDao;
import com.hospitalmis.userManagementModule.entity.RequestChangePassword;
import com.hospitalmis.userManagementModule.entity.Roles;
import com.hospitalmis.userManagementModule.entity.UserAccount;
import com.hospitalmis.userManagementModule.services.IRoleService;
import com.hospitalmis.userManagementModule.services.IUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;


@Slf4j
@Service(IUserInfoService.NAME)
public class UserInfoServiceImplementation implements IUserInfoService {
    @Autowired
    IUsersDao iUsersDao;
    @Autowired
    IRoleService iRoleService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
//    @Transactional
    public String save(UserAccount user, Roles roleName) {
        try {
            UserAccount existingUser = findUserByEmail(user.getEmail());
            if (existingUser != null) {
                throw new IllegalArgumentException("USER ALREADY EXISTS");
            } else {
                user.setFirstname(user.getFirstname());
                user.setLastname(user.getLastname());
                user.setEmail(user.getEmail());
                String randomPassword = generateRandomPassword();
                user.setPassword(passwordEncoder.encode("randomPassword"));
                user.getRoles().add(roleName);
                user.setAccountExpiry(user.getAccountExpiry());
                iUsersDao.save(user);
                sendCredentialsByEmail(user.getEmail(), user.getFirstname() + " " + user.getLastname(), user.getPassword());
                return "user saved";
            }
        } catch (Exception exception) {
            log.error(exception.getLocalizedMessage());
        }
        return "Error out of scope";
    }

    private Roles checkRoleExist() {
        Roles role = new Roles();
        role.setRoleName("ROLE_ADMIN");
        String res=iRoleService.save(role);
        /* TOBE IMPLEMENTED */
        return null;
    }

    @Override
    public void delete(UserAccount userAccount) {
        iUsersDao.delete(userAccount);
    }

    @Override
    public void update(UserAccount userAccount) {
        Optional<UserAccount> existingUsers = iUsersDao.getUsersById(userAccount);
        if (!existingUsers.isPresent()) {
            throw new IllegalArgumentException("UserAccount exists in db ");
        } else {
            iUsersDao.update(userAccount);
        }
    }

    @Override
    public List<Roles> findAll() {
        return iRoleService.findAll();
    }

    @Override
    public UserAccount findUserByEmail(String email) {
        Optional<UserAccount> optionalUserAccount = iUsersDao.searchUserByName(email);
        return optionalUserAccount.orElse(null);
    }

    @Override
    public boolean isTruePassword(UUID userAccountId, String userPassword) throws Exception {
        log.info("-------------" + this.getClass().getName() + "isTruePassword Start");
        UserAccount userAccount = iUsersDao.getAccount(userAccountId);
        if (userAccount != null) {
            log.info("--Find user true--");
            return passwordEncoder.matches(userPassword, userAccount.getPassword());
        } else {
            return false;
        }
    }
    @Override
    public int changeUserPassword(UserAccount userAccount, String newPassword) throws Exception {
        log.info(this.getClass().getName() + "change password starts!");
        UUID userId = userAccount.getUserId();
        String userPassword = userAccount.getPassword();
        boolean checkPassword = isTruePassword(userId, userPassword);
        if (checkPassword) {
            log.info("USER EXISTS WITH MATCHING PASSWORDS");
            userAccount.setPasswordChanged(true); // Mark password as changed
            userAccount.setAccountExpiry(LocalDateTime.now().plusMinutes(30)); // Extend account expiry time
            userAccount.setIsActive(true);
            // Update password and account expiry time in the database
            iUsersDao.updateUserAccountPassword(passwordEncoder.encode(newPassword));
            iUsersDao.update(userAccount);
            log.info(this.getClass().getName() + "change password end");
            return 1;
        }
        log.info(this.getClass().getName() + "change password end");
        return 0;
    }

    @Override
    public String changePassword(RequestChangePassword request) throws Exception {
        UserAccount userAccount = new UserAccount();

        userAccount.setUserId(request.getUserId());
        userAccount.setPassword(request.getOldPassword());

        int check = changeUserPassword(userAccount, request.getNewPassword());

        if (check == 1) {
            return "Password changed successfully";
        } else {
            return "Password not changed";
        }


    }


    private String generateRandomPassword() {
        // Implement logic to generate random password
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    private void sendCredentialsByEmail(String email, String username, String password) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Login in the system usung these credetials:");
            message.setText("Username: " + username + "\nPassword: " + password +
                    "\n\nPlease note that these credentials are valid for only 1 hour.");
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            // Log the exception or handle it
        }
    }

}
