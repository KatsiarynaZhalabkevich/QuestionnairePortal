package com.github.zhalabkevich.dao.impl;

import com.github.zhalabkevich.dao.DAOException;
import com.github.zhalabkevich.dao.UserDAO;
import com.github.zhalabkevich.domain.Users;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class SQLUserDAO implements UserDAO {
    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    @Override
    public void addUser(Users user) throws DAOException {
        try {
            em.persist(user);
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public Users findUserByEmail(String email) throws DAOException {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Users> criteria = criteriaBuilder.createQuery(Users.class);
        Root<Users> users = criteria.from(Users.class);
        criteria.select(users)
                .where(criteriaBuilder.equal(users.get("email"), email));

        return em.createQuery(criteria).getSingleResult();
    }

    @Override
    public boolean isEmailUniq(String email) throws DAOException {
        List<Users> list;
        boolean flag = false;
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Users> criteria = criteriaBuilder.createQuery(Users.class);
            Root<Users> users = criteria.from(Users.class);
            criteria.select(users)
                    .where(criteriaBuilder.equal(users.get("email"), email));
            list = em.createQuery(criteria).getResultList();
            if (list.size() == 0) {
                flag = true;
            }
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }

        return flag;
    }


    @Override
    public Users findUserById(Long id) throws DAOException {
        try {
            return em.find(Users.class, id);
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void updatePassword(Users user) throws DAOException {
        try {
            int count = em.createQuery("update Users set password = :password where id = :id")
                    .setParameter("password", user.getPassword())
                    .setParameter("id", user.getId())
                    .executeUpdate();
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }

    }
@Override
    public boolean updateUserInfo(Users user) throws DAOException {
        try {
            int count = em.createQuery("update Users set firstName= :firstName, lastName=:lastName, phone=:phone where id = :id")
                    .setParameter("firstName", user.getFirstName())
                    .setParameter("lastName", user.getLastName())
                    .setParameter("phone", user.getPhone())
                    .setParameter("id", user.getId())
                    .executeUpdate();
            return count == 1;
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Users> getAllUsers() throws DAOException {
        try {
            return (List<Users>) em.createQuery("select Users from Users").getResultList();
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteUser(Long id) throws DAOException {
        try {
            em.createQuery("delete from Users where id=:id")
                    .setParameter("id", id).executeUpdate();
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }


}
