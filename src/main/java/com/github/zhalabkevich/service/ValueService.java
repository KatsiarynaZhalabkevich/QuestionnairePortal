package com.github.zhalabkevich.service;

import com.github.zhalabkevich.domain.FieldValue;

import java.util.List;

public interface ValueService {
    void saveResponse(List<FieldValue> fvList) throws ServiceException;
    void saveValue(FieldValue value) throws ServiceException;
    List<FieldValue> getAllValues() throws ServiceException;
    List<FieldValue>getValuesByFieldLabel(String label) throws ServiceException;
    List<FieldValue>getValuesByUserId(Long id) throws ServiceException;
    void deleteValuesByFieldId(Long id) throws ServiceException;
    void deleteValuesByUserId(Long id) throws ServiceException;

}
