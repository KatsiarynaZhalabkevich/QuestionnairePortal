package com.github.zhalabkevich.service.impl;

import com.github.zhalabkevich.dao.DAOException;
import com.github.zhalabkevich.dao.FieldDAO;
import com.github.zhalabkevich.domain.Field;
import com.github.zhalabkevich.service.FieldService;
import com.github.zhalabkevich.service.ServiceException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class FieldServiceImpl implements FieldService {
    @EJB
    private FieldDAO fieldDAO;


    @Override
    public void addField(Field field) throws ServiceException {
        try {
            if (fieldDAO.isFieldLabelUniq(field.getLabel())) {
                fieldDAO.addField(field);
            } else {
               throw new ServiceException("Label is not uniq");
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void editField(Field field) throws ServiceException {
        try {
            fieldDAO.updateField(field);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Field getFieldById(Long id) throws ServiceException {
        try {
            fieldDAO.getFieldById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return null;
    }

    @Override
    public Field getFieldByLabel(String label) throws ServiceException {
        try {
            fieldDAO.getFieldByLabel(label);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return null;
    }

    @Override
    public List<Field> getAll() throws ServiceException {
        try {
            return fieldDAO.getFields();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public List<Field> pagination() throws ServiceException {
//        try {
//
//        } catch (DAOException e) {
//            throw new ServiceException(e);
//        } скопировать!!!!
        return null;
    }

    @Override
    public void deleteField(Long id) throws ServiceException {
        try {
            fieldDAO.deleteFieldById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }
}
