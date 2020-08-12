package com.github.zhalabkevich.controller;

import com.github.zhalabkevich.domain.Field;
import com.github.zhalabkevich.domain.FieldValue;
import com.github.zhalabkevich.domain.Users;
import com.github.zhalabkevich.service.ServiceException;
import com.github.zhalabkevich.service.ValueService;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@SessionScoped
public class ValueController implements Serializable {
    private final static Logger logger = LogManager.getLogger(ValueController.class);
    //метод отрабатывает раньше, чем нужно. Без нажатия на кнопку

    private List<String> values = new ArrayList<>();
    private Field field;
    private List<String> singleVal = new ArrayList<>();
    private Map<Field, String> valueMap = new HashMap<>();


    @EJB
    private ValueService valueService;

    public String newResponse(){
        return "index?faces-redirect=true";
    }

    public String saveResponse() {
        System.out.println("Inside save method");
        FacesContext context = FacesContext.getCurrentInstance();
        Users user = (Users) context.getExternalContext().getSessionMap().get("user");
        List<FieldValue> fieldValueList = new ArrayList<>();
        String UUID = java.util.UUID.randomUUID().toString();
        System.out.println(singleVal);
        System.out.println(values);
        System.out.println(valueMap);
        System.out.println("READY TO REDIRECT");

//        for (Field f : valueMap.keySet()) {
//            System.out.println("Field from response via save: " + f);
//            FieldValue fv = new FieldValue();
//            fv.setResponseId(UUID);
//            fv.setField(f);
//            fv.setUser(user);
//            fv.setValue(valueMap.get(f));
//            fieldValueList.add(fv);
//        }
//        try {
//            valueService.saveResponse(fieldValueList);
//            System.out.println("After save");
//            PrimeFaces.current().ajax().update("message");
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Success", "Response was saved!"));
//
//        } catch (ServiceException e) {
//            logger.info(e.getMessage());
//            PrimeFaces.current().ajax().update("message");
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Can't save data to server. Try again letter."));
//        } finally {
//            valueMap = new HashMap<>(); //очищаем
//        }
        return "thanks_page?faces-redirect=true"; //на страницу благодарности
    }

    public String cancel() {
        return "main?faces-redirect=true";
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public List<String> getSingleVal() {
        return singleVal;
    }

    public void setSingleVal(String singleVal) {
        this.singleVal.add(singleVal);
    }

    public Map<Field, String> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<Field, String> valueMap) {
        this.valueMap = valueMap;
    }

    public void setValueMap(Field field, String value) {
        this.valueMap.put(field, value);
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
