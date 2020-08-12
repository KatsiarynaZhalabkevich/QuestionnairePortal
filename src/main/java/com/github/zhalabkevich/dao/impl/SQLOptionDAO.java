package com.github.zhalabkevich.dao.impl;

import com.github.zhalabkevich.dao.DAOException;
import com.github.zhalabkevich.dao.OptionDAO;
import com.github.zhalabkevich.dao.util.HibernateUtil;
import com.github.zhalabkevich.domain.FieldOption;
import org.hibernate.Session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
public class SQLOptionDAO implements OptionDAO {
    @PersistenceContext(unitName = "PU")
    private EntityManager em;


    @Override
    public void addOptionsList(List<FieldOption> options) throws DAOException {
        try {
            for (FieldOption o : options) {
                addOption(o);
            }
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public void addOption(FieldOption option) throws DAOException {
        try {
            em.persist(option);
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<FieldOption> findOptionsByFieldId(Long id) throws DAOException {
        Session session = HibernateUtil.getSession();
        try {
            return (List<FieldOption>) em.createQuery("select o from FieldOption o where Field.id=:id")
                    .setParameter("id", id)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<FieldOption> findOptionsByFieldLabel(String label) throws DAOException {
        try {
            return em.createQuery("select o from FieldOption o where Field.label=:label")
                    .setParameter("label", label)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteOptionsByFieldId(Long id) throws DAOException {
        try {
            em.createQuery("delete  from FieldOption where Field.id=:id")
                    .setParameter("id", id).executeUpdate();
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteOptionsByFieldLabel(String label) throws DAOException {
        try {
            em.createQuery("delete  from FieldOption where Field.label=:label")
                    .setParameter("label", label).executeUpdate();
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteAllOptions() throws DAOException {
        try {
            em.createQuery("delete from FieldOption ").executeUpdate();
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void updateOptionsByFieldId(Long id, List<FieldOption> options) throws DAOException {
        try {
            deleteOptionsByFieldId(id);
            addOptionsList(options);
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }
}
