package com.github.zhalabkevich.service.impl;

import com.github.zhalabkevich.dao.DAOException;
import com.github.zhalabkevich.dao.UserDAO;
import com.github.zhalabkevich.domain.Users;
import com.github.zhalabkevich.service.ServiceException;
import com.github.zhalabkevich.service.UserService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class UserServiceImpl implements UserService {

    @EJB
    private UserDAO userDAO;

    @Override
    public Users findUser(Users user) throws ServiceException {

        try {
            Users userFromBD = userDAO.findUserByEmail(user.getEmail());
            if(user.getPassword().equals(userFromBD.getPassword())){
                return userFromBD;
            }else return null;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Users findUserByEmail(String email) throws ServiceException {
        try {
             return  userDAO.findUserByEmail(email);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addUser(Users user) throws ServiceException {
        try {
            if (!userDAO.isEmailUniq(user.getEmail())) {
                throw new ServiceException("User with such email already exists!");
            }
            userDAO.addUser(user);

        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Users findUserById(Long id) throws ServiceException {
        try {
            return userDAO.findUserById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateUserInfo(Users user) throws ServiceException {
        try {
            userDAO.updateUserInfo(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Users> getAllUsers() throws ServiceException {
        try {
            return userDAO.getAllUsers();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteUser(Long id) throws ServiceException {
        try {
            userDAO.deleteUser(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updatePassword(Users user, String newPassword) throws ServiceException {
        try {
            Users userFromBD = userDAO.findUserById(user.getId());
            if (user.equals(userFromBD)) {
                user.setPassword(newPassword);
                userDAO.updatePassword(user);
                return true;
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return false;
    }

    @Override
    public boolean resetPassword(String email, String newPassword) {
        try {
            Users userFormDB = userDAO.findUserByEmail(email);
            if (userFormDB != null) {
                userFormDB.setPassword(newPassword);
                userDAO.updatePassword(userFormDB);
                return true;
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
