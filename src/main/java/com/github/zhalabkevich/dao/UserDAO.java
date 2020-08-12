package com.github.zhalabkevich.dao;

import com.github.zhalabkevich.domain.Users;

import java.io.Serializable;
import java.util.List;

public interface UserDAO extends Serializable {
    boolean isEmailUniq(String email) throws DAOException;

    Users findUserByEmail(String email) throws DAOException;

    void addUser(Users user) throws DAOException;

    Users findUserById(Long id) throws DAOException;

    List<Users> getAllUsers() throws DAOException;

    void deleteUser(Long id) throws DAOException;

    void updatePassword(Users user) throws DAOException;

    boolean updateUserInfo(Users user) throws DAOException;


}
