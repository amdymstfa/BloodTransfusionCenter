package com.bloodtransfusioncenter.service;

import com.bloodtransfusioncenter.dao.DaoFactory;
import com.bloodtransfusioncenter.dao.RecipientDao;
import com.bloodtransfusioncenter.model.Recipient;
import com.bloodtransfusioncenter.enums.RecipientStatus;
import com.bloodtransfusioncenter.enums.UrgencyLevel;
import com.bloodtransfusioncenter.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Service class for Recipient business logic.
 * Handles recipient operations with validation and business rules.
 */
public class RecipientService {

    private static final Logger logger = LoggerFactory.getLogger(RecipientService.class);
    private static RecipientService instance;

    private final RecipientDao recipientDao;

    private RecipientService() {
        this.recipientDao = DaoFactory.getRecipientDao();
    }

    /**
     * Gets the singleton instance of RecipientService.
     */
    public static synchronized RecipientService getInstance() {
        if (instance == null) {
            instance = new RecipientService();
        }
        return instance;
    }

    /**
     * Creates a new recipient with validation.
     */
    public void createRecipient(Recipient recipient) throws Exception {
        logger.info("Creating new recipient: {}", recipient.getFullName());

        // Validate recipient data
        validateRecipient(recipient);

        // Set initial status
        recipient.setStatus(RecipientStatus.WAITING);

        // Save recipient
        recipientDao.save(recipient);
        logger.info("Recipient created successfully with urgency: {}", recipient.getUrgencyLevel());
    }

    /**
     * Updates an existing recipient.
     */
    public void updateRecipient(Recipient recipient) throws Exception {
        logger.info("Updating recipient ID: {}", recipient.getId());

        if (recipient.getId() == null) {
            throw new Exception("Recipient ID is required for update");
        }

        // Validate recipient data
        validateRecipient(recipient);

        // Update status based on donors count
        updateRecipientStatus(recipient);

        // Update recipient
        recipientDao.update(recipient);
        logger.info("Recipient updated successfully");
    }

    /**
     * Deletes a recipient by ID.
     */
    public void deleteRecipient(Long id) throws Exception {
        logger.info("Deleting recipient ID: {}", id);

        if (id == null) {
            throw new Exception("Recipient ID cannot be null");
        }

        Optional<Recipient> optionalRecipient = recipientDao.findById(id);
        if (!optionalRecipient.isPresent()) {
            throw new Exception("Recipient not found with ID: " + id);
        }

        Recipient recipient = optionalRecipient.get();

        // Check if recipient has associated donors
        if (recipient.getDonors() != null && !recipient.getDonors().isEmpty()) {
            throw new Exception("Cannot delete recipient with associated donors");
        }

        recipientDao.delete(id);
        logger.info("Recipient deleted successfully");
    }

    /**
     * Finds a recipient by ID.
     */
    public Optional<Recipient> findRecipientById(Long id) {
        return recipientDao.findById(id);
    }

    /**
     * Gets all recipients ordered by urgency (CRITICAL first).
     */
    public List<Recipient> getAllRecipientsOrderedByUrgency() {
        return recipientDao.findAllOrderByUrgency();
    }

    /**
     * Gets all waiting recipients.
     */
    public List<Recipient> getWaitingRecipients() {
        return recipientDao.findWaiting();
    }

    /**
     * Gets recipients by urgency level.
     */
    public List<Recipient> getRecipientsByUrgency(UrgencyLevel urgency) {
        return recipientDao.findByUrgencyLevel(urgency);
    }

    /**
     * Gets recipients by status.
     */
    public List<Recipient> getRecipientsByStatus(RecipientStatus status) {
        return recipientDao.findByStatus(status);
    }

    /**
     * Updates the status of a recipient based on donors count.
     */
    public void updateRecipientStatus(Recipient recipient) {
        if (recipient.isSatisfied()) {
            recipient.setStatus(RecipientStatus.SATISFIED);
        } else {
            recipient.setStatus(RecipientStatus.WAITING);
        }
    }

    /**
     * Validates recipient data.
     */
    private void validateRecipient(Recipient recipient) throws Exception {
        if (recipient == null) {
            throw new Exception("Recipient cannot be null");
        }

        // Validate first name
        if (!ValidationUtil.isValidName(recipient.getFirstName())) {
            throw new Exception("Invalid first name");
        }

        // Validate last name
        if (!ValidationUtil.isValidName(recipient.getLastName())) {
            throw new Exception("Invalid last name");
        }

        // Validate phone
        if (!ValidationUtil.isValidPhone(recipient.getPhone())) {
            throw new Exception("Invalid phone number format");
        }

        // Validate CIN
        if (!ValidationUtil.isValidCIN(recipient.getCin())) {
            throw new Exception("Invalid CIN format");
        }

        // Validate birth date
        if (!ValidationUtil.isValidDate(recipient.getBirthDate())) {
            throw new Exception("Invalid birth date");
        }

        // Validate gender
        if (recipient.getGender() == null) {
            throw new Exception("Gender is required");
        }

        // Validate blood type
        if (recipient.getBloodType() == null) {
            throw new Exception("Blood type is required");
        }

        // Validate urgency level
        if (recipient.getUrgencyLevel() == null) {
            throw new Exception("Urgency level is required");
        }
    }
}