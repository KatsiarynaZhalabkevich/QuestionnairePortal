package com.github.zhalabkevich.dao;

import com.github.zhalabkevich.domain.FieldValue;
import com.github.zhalabkevich.service.ServiceException;

import java.util.List;

public interface ValueDAO {
    void addResponse(List<FieldValue> fvList) throws DAOException;
    void addValue(FieldValue value) throws DAOException;
    List<FieldValue> getAllValues() throws DAOException;
    List<FieldValue>getValuesByFieldLabel(String label) throws DAOException;
    List<FieldValue>getValuesByUserId(Long id) throws DAOException;
    void deleteValuesByFieldId(Long id) throws DAOException;
    void deleteValuesByUserId(Long id) throws DAOException;
}
