package com.github.zhalabkevich.dao;

import com.github.zhalabkevich.domain.Field;

import java.util.List;

public interface FieldDAO {
    void addField(Field field) throws DAOException;

    boolean isFieldLabelUniq(String label) throws DAOException;

    Field getFieldByLabel(String label) throws DAOException;

    Field getFieldById(Long id)throws DAOException;

    Field updateField(Field field)throws DAOException;

    List<Field> getFields()throws DAOException;

    void deleteFieldById(Long id)throws DAOException;
}
