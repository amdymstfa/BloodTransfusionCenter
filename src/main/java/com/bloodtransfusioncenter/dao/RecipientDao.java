package com.bloodtransfusioncenter.dao;

import com.bloodtransfusioncenter.model.Recipient;
import com.bloodtransfusioncenter.enums.UrgencyLevel;
import com.bloodtransfusioncenter.enums.RecipientStatus;

import java.util.List;

/**
 * DAO interface for Recipient entity with specific query methods.
 */
public interface RecipientDao extends GenericDao<Recipient> {

    /**
     * Finds all recipients with a specific urgency level.
     * @param urgency the urgency level
     * @return list of recipients with the specified urgency level
     */
    List<Recipient> findByUrgencyLevel(UrgencyLevel urgency);

    /**
     * Finds all recipients with a specific status.
     * @param status the recipient status
     * @return list of recipients with the specified status
     */
    List<Recipient> findByStatus(RecipientStatus status);

    /**
     * Finds all recipients who are waiting for blood.
     * @return list of waiting recipients
     */
    List<Recipient> findWaiting();

    /**
     * Finds all recipients ordered by urgency level (descending).
     * @return list of recipients ordered by urgency (CRITICAL first)
     */
    List<Recipient> findAllOrderByUrgency();

    Recipient findByIdWithDonors(Long id);

}