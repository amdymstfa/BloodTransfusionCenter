package com.bloodtransfusioncenter.util ;

import javax.persistence.EntityManagerFactory ;
import javax.persistence.Persistence ;

/**
 * Manage JPA EntityManagerFactory with utility class
 * Ensure on instance with Singleton pattern
 */

class JPAUtil {
    private static EntityManagerFactory emf ;

    /**
     * Forbid instance of this class
     */

    private JPAUtil(){}

    /**
     * Gets the entity factory instance
     * Create it if not exist
     * @return entity instance
     */

    public static EntityManagerFactory getEntityManagerFactory(){
        if(emf == null){
            try {
                emf = Persistence.createEntityManagerFactory("blood_transfusion_center_PU");
                System.out.println("EntityManagerFactory initialized successfully");
            } catch (Exception e){
                System.err.println("Error creation EntityManagerFactory" + e.getMessage());
                throw new RuntimeException("Failed initialization EntityManagerFactory", e);
            }
        }
        return emf ;
    }

    /**
     * Closes EntityManagerFactory
     * Should be called when the application shuts down
     */
    public static void closeEntityManagerFactory(){
        if(emf != null && emf.isOpen()){
            emf.close();
            System.out.println("EntityManagerFactory closed successfully");
        }
    }
}