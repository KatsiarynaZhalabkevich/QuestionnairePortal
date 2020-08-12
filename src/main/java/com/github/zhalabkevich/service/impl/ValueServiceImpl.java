package com.github.zhalabkevich.service.impl;

import com.github.zhalabkevich.dao.DAOException;
import com.github.zhalabkevich.dao.ValueDAO;
import com.github.zhalabkevich.domain.FieldValue;
import com.github.zhalabkevich.service.ServiceException;
import com.github.zhalabkevich.service.ValueService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
@Stateless
public class ValueServiceImpl implements ValueService {
    @EJB
    ValueDAO valueDAO;

    @Override
    public void saveResponse(List<FieldValue> fvList) throws ServiceException {
        try {
            valueDAO.addResponse(fvList);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void saveValue(FieldValue value) throws ServiceException {
        try {
            valueDAO.addValue(value);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<FieldValue> getAllValues() throws ServiceException {
        try {
            return valueDAO.getAllValues();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public List<FieldValue> getValuesByFieldLabel(String label) throws ServiceException {
        try {
            return valueDAO.getValuesByFieldLabel(label);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    @Override
    public List<FieldValue> getValuesByUserId(Long id) throws ServiceException {
        try {
            return valueDAO.getValuesByUserId(id);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }

    }

    @Override
    public void deleteValuesByFieldId(Long id) throws ServiceException {
        try {
            valueDAO.deleteValuesByFieldId(id);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteValuesByUserId(Long id) throws ServiceException {
        try {
            valueDAO.deleteValuesByUserId(id);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }

    }
}
