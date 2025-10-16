package com.bloodtransfusioncenter.dao.impl ;

import com.bloodtransfusioncenter.dao.GenericDao;
import com.bloodtransfusioncenter.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List ;
import java.util.Optional ;
import java.lang.reflect.ParameterizedType ; // indicate automatically DAO entity which class use

import static com.bloodtransfusioncenter.util.JPAUtil.logger;

public abstract class GenericDaoImpl<T> implements GenericDao<T> {

    private Class<T> entityClass ;

    /**
     * Set automatically the entity class type
     */
    @SuppressWarnings("unchecked")
    public GenericDaoImpl() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void save(T entity) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
            logger.info("Entity saved successfully: {}", entity);
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
                logger.warn("Transaction rolled back due to an error while saving entity: {}", entity, e);
            }
            throw new RuntimeException("Error saving entity: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public void update(T entity){
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(entity);
            tx.commit();
            logger.info("Entity updated successfully : {}", entity);
        } catch (Exception e) {
            if(tx.isActive()){
                tx.rollback();
                logger.warn("Transaction rolled back due to an error while saving entity: {}", entity, e);
            }
            throw new RuntimeException("Error updating entity : " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
                logger.info("Entity deleted successfully: {}", entity);
            } else {
                logger.warn("Entity with id {} not found, nothing to delete", id);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
                logger.warn("Transaction rolled back due to an error while deleting entity with id: {}", id, e);
            }
            throw new RuntimeException("Error deleting entity: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<T> findById(Long id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            T entity = em.find(entityClass, id);
            if (entity != null) {
                logger.info("Entity found with id {}: {}", id, entity);
            } else {
                logger.warn("Entity not found with id {}", id);
            }
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            logger.error("Error finding entity with id {}", id, e);
            throw new RuntimeException("Error finding entity: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<T> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
            List<T> results = em.createQuery(jpql, entityClass).getResultList();
            logger.info("Found {} entities of type {}", results.size(), entityClass.getSimpleName());
            return results;
        } catch (Exception e) {
            logger.error("Error retrieving all entities of type {}", entityClass.getSimpleName(), e);
            throw new RuntimeException("Error retrieving all entities: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}