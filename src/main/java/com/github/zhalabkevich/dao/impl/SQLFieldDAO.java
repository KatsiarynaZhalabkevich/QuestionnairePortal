package com.github.zhalabkevich.dao.impl;

import com.github.zhalabkevich.dao.DAOException;
import com.github.zhalabkevich.dao.FieldDAO;
import com.github.zhalabkevich.domain.Field;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class SQLFieldDAO implements FieldDAO {
    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    @Override
    public void addField(Field field) throws DAOException {
        try {
            em.persist(field);
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public boolean isFieldLabelUniq(String label) throws DAOException {
        List<Field> list;
        boolean flag = false;
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Field> criteria = criteriaBuilder.createQuery(Field.class);
            Root<Field> field = criteria.from(Field.class);
            criteria.select(field)
                    .where(criteriaBuilder.equal(field.get("label"), label));
            list = em.createQuery(criteria).getResultList();
            if (list.size() == 0) {
                flag = true;
            }
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
        return flag;
    }

    @Override
    public Field getFieldByLabel(String label) throws DAOException {
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Field> criteria = criteriaBuilder.createQuery(Field.class);
            Root<Field> field = criteria.from(Field.class);
            criteria.select(field)
                    .where(criteriaBuilder.equal(field.get("label"), label));
            return em.createQuery(criteria).getSingleResult();
        } catch (PersistenceException e) {
            throw new DAOException(e);

        }

    }

    @Override
    public Field getFieldById(Long id) throws DAOException {
        try {
            return em.find(Field.class, id);
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Field updateField(Field field) throws DAOException {
        try {
            int count = em.createQuery("update Field " +
                    "set label=:label, type=:type, required=:required, active=:active " +
                    "where id=:id")
                    .setParameter("label", field.getLabel())
                    .setParameter("type", field.getType())
                    .setParameter("required", field.isRequired())
                    .setParameter("active", field.isActive()).executeUpdate();
            if (count == 1) {
                return field;
            } else return null;
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public List<Field> getFields() throws DAOException {
        try {
            return (List<Field>) em.createQuery("select f from Field f").getResultList();
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteFieldById(Long id) throws DAOException {
        try {
            em.createQuery("delete from Field  where id=:id")
                    .setParameter("id", id)
                    .executeUpdate();
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }

    }
}
