package com.github.zhalabkevich.controller;

import com.github.zhalabkevich.domain.Users;
import com.github.zhalabkevich.service.MailSender;
import com.github.zhalabkevich.service.ServiceException;
import com.github.zhalabkevich.service.UserService;
import org.springframework.util.StringUtils;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class UserController implements Serializable {
    private String email;
    private String password;
    private String password1;
    private String firstName;
    private String lastName;
    private String phone;


    @EJB
    UserService userService;
    @EJB
    MailSender mailSender;

    public String createUser() {
        Users user = new Users(email, password, firstName, lastName, phone);
        try {
            userService.addUser(user);
            if (!StringUtils.isEmpty(user.getEmail())) {
                String message = String.format("Hello, %s %s!\n Welcome to Questionnaire Portal!" +
                                " Visit our site to answer some questions http://localhost:8080/QuestionnairePortal/ ",
                        user.getFirstName(), user.getLastName());
                mailSender.send(user.getEmail(), "Thanks for registration", message);
            }
        } catch (ServiceException e) {

            //такой пользоватнль уже существует
        }
        return "login"; //на страницу информации (юзер в памяти не хранится)
    }

    public String login(String email, String password) {
        FacesContext context = FacesContext.getCurrentInstance();
        Users user = new Users();
        user.setPassword(password);
        user.setEmail(email);
        Users userFromDB = null;
        try {
            userFromDB = userService.findUser(user);
            context.getExternalContext().getSessionMap().put("user", userFromDB);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return userFromDB != null ? "OK" : "Not OK";

    }

    public String updatePassword(String oldPassword, String newPassword) {
        FacesContext context = FacesContext.getCurrentInstance();
        Users user = (Users) context.getExternalContext().getSessionMap().get("user");
        user.setPassword(oldPassword);
        try {
            userService.updatePassword(user, newPassword);
            String message = String.format("Hello, %s %s!\n Your password was updated" +
                            " Visit our site  http://localhost:8080/QuestionnairePortal/ ",
                    user.getFirstName(), user.getLastName());
            mailSender.send(user.getEmail(), "Password updated", message);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return "OK";
    }

   
    public String forgetPassword(String email, String password) {
        boolean flag = false;
        try {
            flag = userService.resetPassword(email, password);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return flag ? "OK" : "Not OK";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
