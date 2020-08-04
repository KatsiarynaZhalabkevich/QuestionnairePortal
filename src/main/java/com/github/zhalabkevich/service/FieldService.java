package com.github.zhalabkevich.service;

import com.github.zhalabkevich.domain.Field;

import java.util.List;

public interface FieldService {

    void addField(Field field) throws ServiceException;

    void editField(Field field) throws ServiceException;

    Field getFieldById(Long id) throws ServiceException;

    Field getFieldByLabel(String label) throws ServiceException;

    List<Field> getAll() throws ServiceException;

    List<Field> pagination() throws ServiceException;

    void deleteField(Long id) throws ServiceException;


}
