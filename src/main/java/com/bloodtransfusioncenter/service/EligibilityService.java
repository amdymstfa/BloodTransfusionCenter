package com.bloodtransfusioncenter.service;

import com.bloodtransfusioncenter.model.Donor;
import com.bloodtransfusioncenter.enums.DonorStatus;

/**
 * Service class to handle donor eligibility logic.
 * Determines if a donor is eligible to donate blood based on medical criteria.
 */
public class EligibilityService {

    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 65;
    private static final double MIN_WEIGHT = 50.0;

    private static EligibilityService instance;

    private EligibilityService() {}

    /**
     * Gets the singleton instance of EligibilityService.
     */
    public static synchronized EligibilityService getInstance() {
        if (instance == null) {
            instance = new EligibilityService();
        }
        return instance;
    }

    /**
     * Determines the eligibility status of a donor.
     *
     * @param donor the donor to evaluate
     * @return the appropriate DonorStatus
     */
    public DonorStatus determineEligibility(Donor donor) {
        if (donor == null) {
            return DonorStatus.NOT_ELIGIBLE;
        }

        // Check age
        int age = donor.getAge();
        if (age < MIN_AGE || age > MAX_AGE) {
            return DonorStatus.NOT_ELIGIBLE;
        }

        // Check weight
        if (donor.getWeight() == null || donor.getWeight() < MIN_WEIGHT) {
            return DonorStatus.NOT_ELIGIBLE;
        }

        // Check contraindications
        if (donor.hasContraindications()) {
            return DonorStatus.NOT_ELIGIBLE;
        }

        // Check if already associated with a recipient
        if (donor.getRecipient() != null) {
            return DonorStatus.NOT_AVAILABLE;
        }

        return DonorStatus.AVAILABLE;
    }

    /**
     * Checks if a donor is eligible based on age criteria.
     */
    public boolean isAgeValid(int age) {
        return age >= MIN_AGE && age <= MAX_AGE;
    }

    /**
     * Checks if a donor's weight meets the requirement.
     */
    public boolean isWeightValid(Double weight) {
        return weight != null && weight >= MIN_WEIGHT;
    }
}