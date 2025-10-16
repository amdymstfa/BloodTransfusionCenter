package com.bloodtransfusioncenter.dao.impl;

import com.bloodtransfusioncenter.dao.RecipientDao;
import com.bloodtransfusioncenter.model.Recipient;
import com.bloodtransfusioncenter.enums.UrgencyLevel;
import com.bloodtransfusioncenter.enums.RecipientStatus;
import com.bloodtransfusioncenter.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implementation of RecipientDao with Singleton pattern.
 */
public class RecipientDaoImpl extends GenericDaoImpl<Recipient> implements RecipientDao {

    private static RecipientDaoImpl instance;

    private RecipientDaoImpl() {
        super();
    }

    /**
     * Gets the singleton instance of RecipientDaoImpl.
     * @return the singleton instance
     */
    public static synchronized RecipientDaoImpl getInstance() {
        if (instance == null) {
            instance = new RecipientDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Recipient> findByUrgencyLevel(UrgencyLevel urgency) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Recipient> query = em.createQuery(
                    "SELECT r FROM Recipient r WHERE r.urgencyLevel = :urgency", Recipient.class);
            query.setParameter("urgency", urgency);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Recipient> findByStatus(RecipientStatus status) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Recipient> query = em.createQuery(
                    "SELECT r FROM Recipient r WHERE r.status = :status", Recipient.class);
            query.setParameter("status", status);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Recipient> findWaiting() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Recipient> query = em.createQuery(
                    "SELECT r FROM Recipient r WHERE r.status = :status", Recipient.class);
            query.setParameter("status", RecipientStatus.WAITING);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Recipient> findAllOrderByUrgency() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Recipient> query = em.createQuery(
                    "SELECT r FROM Recipient r ORDER BY r.urgencyLevel DESC", Recipient.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}