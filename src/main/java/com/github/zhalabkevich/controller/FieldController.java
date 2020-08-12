package com.github.zhalabkevich.controller;

import com.github.zhalabkevich.controller.util.ContextMessage;
import com.github.zhalabkevich.domain.Field;
import com.github.zhalabkevich.domain.FieldOption;
import com.github.zhalabkevich.domain.Type;
import com.github.zhalabkevich.service.FieldService;
import com.github.zhalabkevich.service.OptionService;
import com.github.zhalabkevich.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage actions with fields (CRUD operations)
 */
@Named
@SessionScoped
public class FieldController implements Serializable {

    private final static Logger logger = LogManager.getLogger(FieldController.class);

    private String label;
    private Type type;
    private boolean required;
    private boolean active;
    private List<Field> fields;
    private String optionStr;
    private Type[] types;
    private Field updField;
    private Long updID = 0L;


    private final static String SUCCESS = "SUCCESS";
    private final static String ERROR = "ERROR";

    @EJB
    private FieldService fieldService;
    @EJB
    private OptionService optionService;

   public String add(){
       return "fields/add?faces-redirect=true";
   }
   public String showFields(){
       return "fields/fields?faces-redirect=true";
   }
    public String addField() {

        Field f = new Field(label, type, required, active);
        List<FieldOption> options = convertToFieldOptionList(optionStr, f);
        f.setOptions(options);
        try {
            fieldService.addField(f);
            ContextMessage.contextMessage(SUCCESS, "Field was added!");
            makeDataEmpty();

        } catch (ServiceException e) {
            logger.info(e.getMessage());
            ContextMessage.contextMessage(ERROR, "Field label is not unique!");
        }
        return "fields/add?faces-redirect=true";
    }


    public String deleteField(Long id) {
        try {
            fieldService.deleteField(id);
            ContextMessage.contextMessage(SUCCESS, "Field was removed!");
            fields = fieldService.getAll();

        } catch (ServiceException e) {
            logger.info(e.getMessage());
            ContextMessage.contextMessage(ERROR, "Field was not removed! Try later!");
        }
        return "fields?faces-redirect=true"; //список полей стр переходим или нет?

    }

    public String editField(Field f, Long id) {
        updField = f;
        label = f.getLabel();
        type = f.getType();
        required = f.isRequired();
        active = f.isActive();
        optionStr = "";
        updID = id;
        for (FieldOption fo : f.getOptions()) {
            optionStr += fo.getOption() + "\n";
        }

        return "add?faces-redirect=true";
    }

    public String updateField() {

        updField.setLabel(label);
        updField.setType(type);
        updField.setRequired(required);
        updField.setActive(active);
        updField.setOptions(convertToFieldOptionList(optionStr, updField));
        try {
            optionService.deleteOptionsByFieldId(getUpdID());
            fieldService.editField(updField);
            ContextMessage.contextMessage(SUCCESS, "Field was updated!");
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            ContextMessage.contextMessage(ERROR, "Can't get data from server. Try again later!");
        }
        return cancel();
    }



    public List<Field> getFields() {
        try {
            return fieldService.getAll();
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            ContextMessage.contextMessage(SUCCESS, "Can't get data from server. Please, try later!");
        }
        return null;
    }


    public String cancel() {
        makeDataEmpty();
        return "fields/fields?faces-redirect=true";
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

    public Field getUpdField() {
        return updField;
    }

    public void setUpdField(Field updField) {
        this.updField = updField;
    }

    public Long getUpdID() {
        return updID;
    }

    public void setUpdID(Long updID) {
        this.updID = updID;
    }

    private List<FieldOption> convertToFieldOptionList(String optionsStr, Field field) {
        List<FieldOption> options = new ArrayList<>();
        String[] lst = optionsStr.trim().split("\n");
        for (String s : lst) {
            FieldOption option = new FieldOption();
            option.setField(field);
            option.setOption(s);
            options.add(option);
        }
        return options;
    }

    private void makeDataEmpty() {
        label = null;
        type = null;
        required = false;
        active = false;
        optionStr = "";
        updID = 0L;
    }
}
