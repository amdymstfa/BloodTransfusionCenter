package com.bloodtransfusioncenter.dao.impl;

import com.bloodtransfusioncenter.dao.DonorDao;
import com.bloodtransfusioncenter.model.Donor;
import com.bloodtransfusioncenter.enums.BloodType;
import com.bloodtransfusioncenter.enums.DonorStatus;
import com.bloodtransfusioncenter.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implementation of DonorDao with Singleton pattern.
 */
public class DonorDaoImpl extends GenericDaoImpl<Donor> implements DonorDao {

    private static DonorDao instance ;

    private DonorDaoImpl(){
        super();
    }

    /**
     * Gets a single instance of DonorDaoImpl
     * @return a singleton instance
     */
    public static synchronized DonorDaoImpl getInstance() {
        if (instance == null) {
            instance = new DonorDaoImpl();
        }
        return (DonorDaoImpl) instance;
    }

    @Override
    public List<Donor> findByBloodType(BloodType bloodType){
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager() ;

        try {
            TypedQuery<Donor> query = em.createQuery(
                    "SELECT d FROM Donor d WHERE d.bloodType = :bloodType", Donor.class
            );
            query.setParameter("bloodType", bloodType);
            return query.getResultList();
        }finally {
            em.close();
        }
    }

    @Override
    public List<Donor> findAvailable() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Donor> query = em.createQuery(
                    "SELECT d FROM Donor d WHERE d.status = :status", Donor.class);
            query.setParameter("status", DonorStatus.AVAILABLE);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Donor> findByStatus(DonorStatus status) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Donor> query = em.createQuery(
                    "SELECT d FROM Donor d WHERE d.status = :status", Donor.class);
            query.setParameter("status", status);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}