package com.github.zhalabkevich.service;

import com.github.zhalabkevich.dao.DAOException;
import com.github.zhalabkevich.domain.FieldOption;

import java.util.List;

public interface OptionService {
    void addOptionsList(List<FieldOption> options) throws ServiceException;
    void addOption(FieldOption option) throws ServiceException;
    List<FieldOption> findOptionsByFieldId(Long id)throws ServiceException;
    List<FieldOption> findOptionsByFieldLabel(String label)throws ServiceException;
    void deleteOptionsByFieldId(Long id)throws ServiceException;
    void deleteOptionsByFieldLabel(String label)throws ServiceException;
    void deleteAllOptions()throws ServiceException;
    void updateOptionsByFieldId(Long id, List<FieldOption> options)throws ServiceException;
}
