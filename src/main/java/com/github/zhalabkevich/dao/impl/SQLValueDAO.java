package com.github.zhalabkevich.dao.impl;

import com.github.zhalabkevich.dao.DAOException;
import com.github.zhalabkevich.dao.ValueDAO;
import com.github.zhalabkevich.domain.FieldValue;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
@Stateless
public class SQLValueDAO implements ValueDAO {
    @PersistenceContext(name = "PU")
    private EntityManager em;

    @Override
    public void addResponse(List<FieldValue> fvList) throws DAOException {
        try {
            for (FieldValue fv : fvList
            ) {
                addValue(fv);
            }
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void addValue(FieldValue value) throws DAOException {
        try {
            em.persist(value);
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<FieldValue> getAllValues() throws DAOException {
        try {
            return em.createQuery("select v from FieldValue v").getResultList();
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public List<FieldValue> getValuesByFieldLabel(String label) throws DAOException {
        try {
            return em.createQuery("select v from FieldValue v " +
                    "join Field f on v.field.id=f.id " +
                    "join Users u on v.user.id=u.id" +
                    " where f.label=:label")
                    .setParameter("label", label).getResultList();
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public List<FieldValue> getValuesByUserId(Long id) throws DAOException {
        try {
            return em.createQuery("select v from FieldValue v " +
                    "join Field f on v.field.id=f.id " +
                    "join Users u on v.user.id=u.id" +
                    " where u.id=:id")
                    .setParameter("id", id).getResultList();
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public void deleteValuesByFieldId(Long id) throws DAOException {
        try {
            em.createQuery("delete from FieldValue v " +
                    "where v.field.id=:id ")
                    .setParameter("id", id).executeUpdate();
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public void deleteValuesByUserId(Long id) throws DAOException {
        try {em.createQuery("delete from FieldValue v " +
                "where v.user.id=:id ")
                .setParameter("id", id).executeUpdate();
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }

    }
}
