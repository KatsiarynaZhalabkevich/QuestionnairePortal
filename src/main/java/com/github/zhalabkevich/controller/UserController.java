package com.github.zhalabkevich.controller;

import com.github.zhalabkevich.controller.util.ContextMessage;
import com.github.zhalabkevich.domain.Users;
import com.github.zhalabkevich.service.ServiceException;
import com.github.zhalabkevich.service.UserService;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class UserController implements Serializable {
    private final static Logger logger = LogManager.getLogger(UserController.class);

    private String email;
    private String password;
    private String password1;
    private String password2;
    private String firstName;
    private String lastName;
    private String phone;
    private String secretCode;
    private String UUID;

    private static final String ERROR = "Error";
    private static final String SUCCESS = "Success";


    @EJB
    UserService userService;

    //   @Inject- NullPointerException
//    MailSender mailSender;

    public String login() {
        return "registration/login";
    }

    public String signIn() {
        return "registration/create";
    }

    public String resetPas() {
        return "registration/reset_password";
    }

    public String edit() {
        return "users/edit_profile";
    }
    public String changePass(){
        return "users/change_password";
    }

    public String createUser() {
        Users user = new Users(email, password, firstName, lastName, phone);
        try {
            userService.addUser(user);
            ContextMessage.contextMessage(SUCCESS, "New user was created!");

//            if (!StringUtils.isEmpty(user.getEmail())) {
//                String message = String.format("Hello, %s %s!\n Welcome to Questionnaire Portal!" +
//                                " Visit our site to answer some questions http://localhost:8080/QuestionnairePortal/ ",
//                        user.getFirstName(), user.getLastName());
//                mailSender.send(user.getEmail(), "Thanks for registration", message);
//            }
            makeEmpty();
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            ContextMessage.contextMessage(ERROR, "User with such email already exists!");
        }
        return "registration/login?faces-redirect=true"; //на страницу логина нужно залогиниться
    }

    public String login(String email, String password) {
        FacesContext context = FacesContext.getCurrentInstance();
        Users user = new Users(email, password);
        Users userFromDB = null;
        try {
            userFromDB = userService.findUser(user);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            ContextMessage.contextMessage(ERROR, "Can't get data from service. Please try letter!");
        }
        if (userFromDB != null) {
            ContextMessage.contextMessage(SUCCESS, "You are logged In");
            context.getExternalContext().getSessionMap().put("user", userFromDB);
            System.out.println("REady to redirect");
            return "main?faces-redirect=true";
        } else {
            ContextMessage.contextMessage(ERROR, "Wrong login and/or password! Please try again! ");
            return "registration/login?faces-redirect=true"; //оставить на той же странице
        }
    }


    public String updatePassword(String oldPassword, String newPassword) {
        FacesContext context = FacesContext.getCurrentInstance();
        Users user = (Users) context.getExternalContext().getSessionMap().get("user");
        if (user.getPassword().equals(oldPassword)) {
            try {
                if (userService.updatePassword(user, newPassword)) {
                    user.setPassword(newPassword);
                    context.getExternalContext().getSessionMap().put("user", user);
                    ContextMessage.contextMessage(SUCCESS, "Password was updated!");
                    //            String message = String.format("Hello, %s %s!\n Your password was updated" +
                    //                            " Visit our site  http://localhost:8080/QuestionnairePortal/ ",
                    //                    user.getFirstName(), user.getLastName());
                    //            mailSender.send(user.getEmail(), "Password updated", message);
                } else {
                    ContextMessage.contextMessage(ERROR, "Password was not updated!");
                }
            } catch (ServiceException e) {
                logger.info(e.getMessage());
                ContextMessage.contextMessage(ERROR, "Can't get data from service. Please try later!");
            }
        }
        return "main?faces-redirect=true";
    }


    public void sendSecretCode(String email) {
        //  UUID = java.util.UUID.randomUUID().toString();
        // секретный код отправить на почту, ввести его и если все ок, то сбросить пароль
        //пока без секретного ключа (нужна доп страница для скретного ключа)
//                        String message = String.format("Your secret code is  %s " +
//                                "enter it in a field  on link" +
//                                "  http://localhost:8080/QuestionnairePortal/reset_password.xhml ", UUID
//                        );
//                        mailSender.send(email, "Secret code to reset password", message);
    }


    public String forgetPassword(String email, String password) {
        if (UUID.equals(secretCode)) {
            try {
                if (userService.resetPassword(email, password)) {
                    ContextMessage.contextMessage(SUCCESS, "Password was changed!");
                } else {
                    ContextMessage.contextMessage(ERROR, "Can't get data from service. Please try later!");
                }

            } catch (ServiceException e) {
                logger.info(e.getMessage());
                ContextMessage.contextMessage(ERROR, "Can't get data from service. Please try later!");
            }
        } else {
            ContextMessage.contextMessage(ERROR, "Code is invalid!");
        }

        return "registration/login?faces-redirect=true"; //не был зарегистрирован, идет на страницу логина
    }


    public String editProfile() {
        FacesContext context = FacesContext.getCurrentInstance();
        Users user = (Users) context.getExternalContext().getSessionMap().get("user");
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        try {
            userService.updateUserInfo(user);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            ContextMessage.contextMessage(ERROR, "Can't get data from service. Please try later!");
        }
        return "main?faces-redirect=true"; //другой адрес
    }

    public String cancel() {
        return "main?faces-redirect=true";
    }


    public String logOut() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("user");
        return "main?faces-redirect=true";
    }

    private void makeEmpty() {
        email = null;
        password = null;
        password1 = null;
        firstName = null;
        lastName = null;
        phone = null;
        secretCode = null;
        UUID = null;

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

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
