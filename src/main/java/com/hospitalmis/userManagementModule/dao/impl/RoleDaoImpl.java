package com.hospitalmis.userManagementModule.dao.impl;

import com.hospitalmis.userManagementModule.dao.IRolesDao;
import com.hospitalmis.userManagementModule.entity.Roles;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RoleDaoImpl implements IRolesDao {
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Roles> queryAll() {
        return em.createNamedQuery("Roles.listActiveRoles", Roles.class)
                .getResultList();
    }



    @Transactional(readOnly = true)
    public Roles get(UUID id) {
        return em.find(Roles.class, id);
    }

    @Transactional
    public Roles save(Roles role) {
        em.persist(role);
        em.flush();
        return role;
    }

    @Transactional
    public Roles update(Roles users) {
        em.merge(users);
        return users;
    }


    @Transactional(readOnly = true)
    public Optional<Roles> searchRoleByName(String keyword) {
        List<Roles> resultList = em.createNamedQuery("Roles.searchRoleByName", Roles.class)
                .setParameter("kw", "%" + keyword + "%")
                .getResultList();

        // Assuming the query returns at most one user, hence fetching the first element of the list
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }

    @Override
    public Roles getRoleByName(String roles) {
        Query query=em.createQuery("SELECT r FROM Roles r WHERE r.roleName =:kw and r.isActive=true");
        query.setParameter("kw", roles);
        return (Roles) query;
    }


    @Transactional
    public void delete(Roles role) {
        Roles r = get(role.getRoleId());
        if(r != null) {
            role.setActive(false);
            em.merge(role);
        }
    }

}
