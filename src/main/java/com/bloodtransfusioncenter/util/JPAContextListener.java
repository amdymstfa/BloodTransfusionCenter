package com.bloodtransfusioncenter.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Manage JPA life cycle with servlet container listener
 * Ensure closing of database when application shutdown
 */
public class JPAContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce){
        System.out.println("Starting - Initializing JPA");
        try{
            JPAUtil.getEntityManagerFactory();
            System.out.println("JPA initialized successfully");
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize JPA", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce){
        System.out.println("Shutting down");
        try {
            JPAUtil.closeEntityManagerFactory();
            System.out.println("JPA closing successfully");
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to close JPA", e);
        }
    }
}
