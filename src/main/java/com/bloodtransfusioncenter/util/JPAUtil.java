package com.bloodtransfusioncenter.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to manage the JPA EntityManagerFactory.
 * Implements the Singleton pattern to ensure a single shared instance
 * across the entire application.
 */
public class JPAUtil {

    public static final Logger logger = LoggerFactory.getLogger(JPAUtil.class);
    private static EntityManagerFactory emf;

    /**
     * Private constructor to prevent instantiation.
     */
    private JPAUtil() {}

    /**
     * Returns the singleton instance of EntityManagerFactory.
     * Initializes it if it doesn't already exist.
     */
    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            try {
                emf = Persistence.createEntityManagerFactory("blood_transfusion_center_PU");
                logger.info("EntityManagerFactory initialized successfully ");
            } catch (Exception e) {
                logger.error("Error creating EntityManagerFactory", e);
                throw new RuntimeException("Failed to initialize EntityManagerFactory", e);
            }
        }
        return emf;
    }

    /**
     * Closes the EntityManagerFactory if it is open.
     * Should be called when the application shuts down.
     */
    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            logger.info("EntityManagerFactory closed successfully");
        }
    }
}
