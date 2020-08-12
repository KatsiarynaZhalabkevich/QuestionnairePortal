package com.github.zhalabkevich.dao;

import com.github.zhalabkevich.domain.FieldOption;

import java.util.List;

public interface OptionDAO {
    void addOptionsList(List<FieldOption> options) throws DAOException;
    void addOption(FieldOption option) throws DAOException;
    List<FieldOption> findOptionsByFieldId(Long id)throws DAOException;
    List<FieldOption> findOptionsByFieldLabel(String label)throws DAOException;
    void deleteOptionsByFieldId(Long id)throws DAOException;
    void deleteOptionsByFieldLabel(String label)throws DAOException;
    void deleteAllOptions()throws DAOException;
    void updateOptionsByFieldId(Long id, List<FieldOption> options)throws DAOException;
}
