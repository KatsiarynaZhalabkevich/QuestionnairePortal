package com.github.zhalabkevich.service.impl;

import com.github.zhalabkevich.dao.DAOException;
import com.github.zhalabkevich.dao.OptionDAO;
import com.github.zhalabkevich.domain.FieldOption;
import com.github.zhalabkevich.service.OptionService;
import com.github.zhalabkevich.service.ServiceException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class OptionServiceImpl implements OptionService {
    @EJB
    private OptionDAO optionDAO;

    @Override
    public void addOptionsList(List<FieldOption> options) throws ServiceException {
        try {
            optionDAO.addOptionsList(options);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addOption(FieldOption option) throws ServiceException {
        try {
            optionDAO.addOption(option);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<FieldOption> findOptionsByFieldId(Long id) throws ServiceException {
        try {
            return optionDAO.findOptionsByFieldId(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public List<FieldOption> findOptionsByFieldLabel(String label) throws ServiceException {
        try {
            return optionDAO.findOptionsByFieldLabel(label);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void deleteOptionsByFieldId(Long id) throws ServiceException {
        try {
            optionDAO.deleteOptionsByFieldId(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteOptionsByFieldLabel(String label) throws ServiceException {
        try {
            optionDAO.deleteOptionsByFieldLabel(label);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteAllOptions() throws ServiceException {
        try {
            optionDAO.deleteAllOptions();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateOptionsByFieldId(Long id, List<FieldOption> options) throws ServiceException {
        try {
            optionDAO.updateOptionsByFieldId(id, options);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
