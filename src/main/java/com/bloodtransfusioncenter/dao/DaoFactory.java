package com.bloodtransfusioncenter.dao;

import com.bloodtransfusioncenter.dao.impl.DonorDaoImpl;
import com.bloodtransfusioncenter.dao.impl.RecipientDaoImpl;

/**
 * Factory class to provide DAO instances.
 * Centralizes DAO access using Singleton pattern.
 * Provides a single point of access to all DAO implementations.
 */
public class DaoFactory {

    /**
     * Private constructor to prevent instantiation.
     */
    private DaoFactory() {
        // Utility class, no instantiation allowed
    }

    /**
     * Gets the DonorDao singleton instance.
     *
     * @return the DonorDao implementation
     */
    public static DonorDao getDonorDao() {
        return DonorDaoImpl.getInstance();
    }

    /**
     * Gets the RecipientDao singleton instance.
     *
     * @return the RecipientDao implementation
     */
    public static RecipientDao getRecipientDao() {
        return RecipientDaoImpl.getInstance();
    }
}