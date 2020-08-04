package com.github.zhalabkevich.service;

import com.github.zhalabkevich.domain.Users;

import java.util.List;

public interface UserService {

    Users findUser(Users user) throws ServiceException;

    Users findUserByEmail(String email) throws ServiceException;

    void addUser(Users user) throws ServiceException;

    Users findUserById(Long id) throws ServiceException;

    List<Users> getAllUsers() throws ServiceException;

    void deleteUser(Long id) throws ServiceException;

    boolean updatePassword(Users user, String newPassword) throws ServiceException;

    boolean resetPassword(String email, String newPassword) throws ServiceException;
}
