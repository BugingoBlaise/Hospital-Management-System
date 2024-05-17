package com.hospitalmis.userManagementModule.dao.impl;

import com.hospitalmis.userManagementModule.dao.IUsersDao;
import com.hospitalmis.userManagementModule.entity.Roles;
import com.hospitalmis.userManagementModule.entity.UserAccount;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserDaoImpl implements IUsersDao {
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<UserAccount> queryAll() {
        Query query = em.createQuery("SELECT u FROM UserAccount u");
        List<UserAccount> result = query.getResultList();
        return result;
    }

    @Transactional(readOnly = true)
    public UserAccount get(UUID id) {
        return em.find(UserAccount.class, id);
    }

    @Transactional
    public void save(UserAccount user) {
        em.persist(user);
        em.flush();
    }

    @Transactional
    public UserAccount update(UserAccount userAccount) {
        em.merge(userAccount);
        return userAccount;
    }
    @Transactional
    public Optional<UserAccount> getUsersById(UserAccount userAccount) {
        return Optional.ofNullable(em.find(UserAccount.class, userAccount.getUserId()));
    }
    @Transactional
        public Optional<UserAccount> findByLastnameOrFirstname(String keyword) {

                List<UserAccount>resultList=em.createNamedQuery("UserAccount.findByLastnameOrFirstname", UserAccount.class)
                .setParameter("name", "%" + keyword + "%").getResultList(); // Use wildcard (%) to match any part of the name

    return resultList.isEmpty()? Optional.empty() : Optional.of(resultList.get(0));
    }
    @Transactional(readOnly = true)
    public Optional<UserAccount> searchUserByName(String keyword) {
        List<UserAccount> resultList = em.createNamedQuery("UserAccount.searchUserByEmail", UserAccount.class)
                .setParameter("kw", "%" + keyword + "%")
                .getResultList();

        // Assuming the query returns at most one user, hence fetching the first element of the list
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }

    @Override
    public UserAccount getAccount(UUID accountId) {
        Query query = em.createQuery("SELECT u FROM UserAccount u WHERE u.id = :p");
        query.setParameter("p", accountId);

        // Execute the query and get the single result

        return (UserAccount) query.getSingleResult();
    }

    @Override
    public void updateUserAccountPassword(String newPassword) {
        Query query = em.createNamedQuery("UserAccount.updateUserAccountPassword");
        query.setParameter("p", newPassword);
        int rowsUpdated = query.executeUpdate();
        if (rowsUpdated == 0) {
            throw new NoResultException("Update not done  ");
        }

    }

    @Transactional
    public void delete(UserAccount user) {
        UserAccount userAccount = get(user.getUserId());
        if(userAccount != null) {
            user.setIsActive(false);
            em.merge(user);
        }
    }

}
