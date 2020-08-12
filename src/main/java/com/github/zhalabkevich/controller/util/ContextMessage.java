package com.github.zhalabkevich.controller.util;

import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class ContextMessage {
    private final static String SUCCESS = "SUCCESS";
    private final static String ERROR = "ERROR";

    public static void contextMessage(String kind, String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        PrimeFaces.current().ajax().update("message");
        if (SUCCESS.equals(kind)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, kind, message));
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, kind, message));
        }

    }
}
