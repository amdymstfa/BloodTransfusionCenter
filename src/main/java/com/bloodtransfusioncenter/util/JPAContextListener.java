package com.bloodtransfusioncenter.util;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Manage JPA life cycle with servlet contain listener
 * Ensure closing of database when application shutdown
 */

class JPAContextListener implements ServletContextListener {

    /**
     * Called when the application is start
     * Initializes EntityManagerFactory
     */

    @Override
    public void contextInitilized(ServletContextEven sce){
        System.out.println("Starting - Initializing JPA");
        try{
            JPAUtil.getEntityManagerFactory();
            System.out.print("JPA initialized successfully");
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize JPA", e);
        }
    }

    /**
     * Called when the application shutting down
     * Close EntityManagerFactory
     */
    public void contextDestroy(ServletContextEven sce){
        System.out.println("Shutting down");
        try {
            JPAUtil.closeEntityManagerFactory();
            System.out.println("JPA closing successfully");
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed of closing" , e);
        }
    }


}