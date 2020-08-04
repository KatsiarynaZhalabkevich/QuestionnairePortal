package com.github.zhalabkevich.controller;

import com.github.zhalabkevich.domain.Field;
import com.github.zhalabkevich.domain.FieldOption;
import com.github.zhalabkevich.domain.Type;
import com.github.zhalabkevich.service.FieldService;
import com.github.zhalabkevich.service.ServiceException;
import org.primefaces.PrimeFaces;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class FieldController implements Serializable {

    private String label;
    private Type type;
    private boolean required;
    private boolean active;
    private List<Field> fields; //зачем лист филдов? чтобы отобразить их всех
    private String optionStr;
    private Type[] types;

    @EJB
    private FieldService fieldService;

    public String addField(String label, Type type, boolean required, boolean active, String optionStr) {
        Field field = new Field(label, type, required, active);
        field.setOptions(convertToFieldOptionList(optionStr));
        try {
            fieldService.addField(field);
        } catch (ServiceException e) {
            PrimeFaces.current().ajax().update("growl");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error","Field label is not unique!"));

        }
        return "OK";
    }

    public String deleteField(Long id){
        try {
            fieldService.deleteField(id);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return "OK";
    }
    //
    public String editField(Long id){
        return "fields/fields";
    }

    //перенести в логику
    private List<FieldOption> convertToFieldOptionList(String optionsStr) {
        List<FieldOption> options = new ArrayList<>();
        String[] lst = optionsStr.trim().split("\n");
        for (String s : lst) {
            FieldOption option = new FieldOption();
            //option.setField(field);
            option.setOption(s);
            options.add(option);
        }
        return options;
    }

    public List<Field> getFields() {
        try {
            return fieldService.getAll();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public String getOptionStr() {
        return optionStr;
    }

    public void setOptionStr(String optionStr) {
        this.optionStr = optionStr;
    }

    public Type[] getTypes() {
        return Type.values();
    }

    public void setTypes(Type[] types) {
        this.types = types;
    }
}
