package com.bloodtransfusioncenter.service;

import com.bloodtransfusioncenter.dao.DaoFactory;
import com.bloodtransfusioncenter.dao.DonorDao;
import com.bloodtransfusioncenter.model.Donor;
import com.bloodtransfusioncenter.enums.BloodType;
import com.bloodtransfusioncenter.enums.DonorStatus;
import com.bloodtransfusioncenter.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Service class for Donor business logic.
 * Handles donor operations with validation and business rules.
 */
public class DonorService {

    private static final Logger logger = LoggerFactory.getLogger(DonorService.class);
    private static DonorService instance;

    private final DonorDao donorDao;
    private final EligibilityService eligibilityService;

    private DonorService() {
        this.donorDao = DaoFactory.getDonorDao();
        this.eligibilityService = EligibilityService.getInstance();
    }

    /**
     * Gets the singleton instance of DonorService.
     */
    public static synchronized DonorService getInstance() {
        if (instance == null) {
            instance = new DonorService();
        }
        return instance;
    }

    /**
     * Creates a new donor with validation and eligibility check.
     */
    public void createDonor(Donor donor) throws Exception {
        logger.info("Creating new donor: {}", donor.getFullName());

        // Validate donor data
        validateDonor(donor);

        // Determine eligibility status
        DonorStatus status = eligibilityService.determineEligibility(donor);
        donor.setStatus(status);

        // Save donor
        donorDao.save(donor);
        logger.info("Donor created successfully with status: {}", status);
    }

    /**
     * Updates an existing donor.
     */
    public void updateDonor(Donor donor) throws Exception {
        logger.info("Updating donor ID: {}", donor.getId());

        if (donor.getId() == null) {
            throw new Exception("Donor ID is required for update");
        }

        // Validate donor data
        validateDonor(donor);

        // Recalculate eligibility status
        DonorStatus status = eligibilityService.determineEligibility(donor);
        donor.setStatus(status);

        // Update donor
        donorDao.update(donor);
        logger.info("Donor updated successfully");
    }

    /**
     * Deletes a donor by ID.
     */
    public void deleteDonor(Long id) throws Exception {
        logger.info("Deleting donor ID: {}", id);

        if (id == null) {
            throw new Exception("Donor ID cannot be null");
        }

        Optional<Donor> optionalDonor = donorDao.findById(id);
        if (!optionalDonor.isPresent()) {
            throw new Exception("Donor not found with ID: " + id);
        }

        Donor donor = optionalDonor.get();

        // Check if donor is associated with a recipient
        if (donor.getRecipient() != null) {
            throw new Exception("Cannot delete donor associated with a recipient");
        }

        donorDao.delete(id);
        logger.info("Donor deleted successfully");
    }

    /**
     * Finds a donor by ID.
     */
    public Optional<Donor> findDonorById(Long id) {
        return donorDao.findById(id);
    }

    /**
     * Gets all donors.
     */
    public List<Donor> getAllDonors() {
        return donorDao.findAll();
    }

    /**
     * Gets all available donors.
     */
    public List<Donor> getAvailableDonors() {
        return donorDao.findAvailable();
    }

    /**
     * Gets donors by blood type.
     */
    public List<Donor> getDonorsByBloodType(BloodType bloodType) {
        return donorDao.findByBloodType(bloodType);
    }

    /**
     * Gets donors by status.
     */
    public List<Donor> getDonorsByStatus(DonorStatus status) {
        return donorDao.findByStatus(status);
    }

    /**
     * Validates donor data.
     */
    private void validateDonor(Donor donor) throws Exception {
        if (donor == null) {
            throw new Exception("Donor cannot be null");
        }

        // Validate first name
        if (!ValidationUtil.isValidName(donor.getFirstName())) {
            throw new Exception("Invalid first name");
        }

        // Validate last name
        if (!ValidationUtil.isValidName(donor.getLastName())) {
            throw new Exception("Invalid last name");
        }

        // Validate phone
        if (!ValidationUtil.isValidPhone(donor.getPhone())) {
            throw new Exception("Invalid phone number format");
        }

        // Validate CIN
        if (!ValidationUtil.isValidCIN(donor.getCin())) {
            throw new Exception("Invalid CIN format");
        }

        // Validate birth date
        if (!ValidationUtil.isValidDate(donor.getBirthDate())) {
            throw new Exception("Invalid birth date");
        }

        // Validate age
        if (!ValidationUtil.isValidAge(donor.getBirthDate(), 18, 65)) {
            throw new Exception("Donor must be between 18 and 65 years old");
        }

        // Validate weight
        if (!ValidationUtil.isValidWeight(donor.getWeight(), 50.0)) {
            throw new Exception("Donor must weigh at least 50 kg");
        }

        // Validate gender
        if (donor.getGender() == null) {
            throw new Exception("Gender is required");
        }

        // Validate blood type
        if (donor.getBloodType() == null) {
            throw new Exception("Blood type is required");
        }
    }
}