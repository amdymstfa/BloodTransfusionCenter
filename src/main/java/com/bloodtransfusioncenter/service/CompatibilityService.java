package com.bloodtransfusioncenter.service;

import com.bloodtransfusioncenter.enums.BloodType;
import com.bloodtransfusioncenter.model.Donor;
import com.bloodtransfusioncenter.model.Recipient;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class to handle blood type compatibility logic.
 * Implements the blood compatibility matrix.
 */
public class CompatibilityService {

    private static CompatibilityService instance;
    private final Map<BloodType, List<BloodType>> compatibilityMatrix;

    private CompatibilityService() {
        this.compatibilityMatrix = initializeCompatibilityMatrix();
    }

    /**
     * Gets the singleton instance of CompatibilityService.
     */
    public static synchronized CompatibilityService getInstance() {
        if (instance == null) {
            instance = new CompatibilityService();
        }
        return instance;
    }

    /**
     * Initializes the blood type compatibility matrix.
     * Each blood type maps to the list of recipient blood types it can donate to.
     */
    private Map<BloodType, List<BloodType>> initializeCompatibilityMatrix() {
        Map<BloodType, List<BloodType>> matrix = new HashMap<>();

        // O- is universal donor (can donate to all)
        matrix.put(BloodType.O_NEGATIVE, Arrays.asList(
                BloodType.O_NEGATIVE, BloodType.O_POSITIVE,
                BloodType.A_NEGATIVE, BloodType.A_POSITIVE,
                BloodType.B_NEGATIVE, BloodType.B_POSITIVE,
                BloodType.AB_NEGATIVE, BloodType.AB_POSITIVE
        ));

        // O+
        matrix.put(BloodType.O_POSITIVE, Arrays.asList(
                BloodType.O_POSITIVE,
                BloodType.A_POSITIVE,
                BloodType.B_POSITIVE,
                BloodType.AB_POSITIVE
        ));

        // A-
        matrix.put(BloodType.A_NEGATIVE, Arrays.asList(
                BloodType.A_NEGATIVE, BloodType.A_POSITIVE,
                BloodType.AB_NEGATIVE, BloodType.AB_POSITIVE
        ));

        // A+
        matrix.put(BloodType.A_POSITIVE, Arrays.asList(
                BloodType.A_POSITIVE,
                BloodType.AB_POSITIVE
        ));

        // B-
        matrix.put(BloodType.B_NEGATIVE, Arrays.asList(
                BloodType.B_NEGATIVE, BloodType.B_POSITIVE,
                BloodType.AB_NEGATIVE, BloodType.AB_POSITIVE
        ));

        // B+
        matrix.put(BloodType.B_POSITIVE, Arrays.asList(
                BloodType.B_POSITIVE,
                BloodType.AB_POSITIVE
        ));

        // AB-
        matrix.put(BloodType.AB_NEGATIVE, Arrays.asList(
                BloodType.AB_NEGATIVE,
                BloodType.AB_POSITIVE
        ));

        // AB+ is universal recipient (but can only donate to AB+)
        matrix.put(BloodType.AB_POSITIVE, Arrays.asList(
                BloodType.AB_POSITIVE
        ));

        return matrix;
    }

    /**
     * Checks if a donor's blood type is compatible with a recipient's blood type.
     */
    public boolean isCompatible(BloodType donorType, BloodType recipientType) {
        if (donorType == null || recipientType == null) {
            return false;
        }

        List<BloodType> compatibleTypes = compatibilityMatrix.get(donorType);
        return compatibleTypes != null && compatibleTypes.contains(recipientType);
    }

    /**
     * Checks if a donor can donate to a recipient.
     */
    public boolean isCompatible(Donor donor, Recipient recipient) {
        if (donor == null || recipient == null) {
            return false;
        }
        return isCompatible(donor.getBloodType(), recipient.getBloodType());
    }

    /**
     * Filters a list of donors to return only those compatible with a recipient.
     */
    public List<Donor> getCompatibleDonors(List<Donor> donors, Recipient recipient) {
        if (donors == null || recipient == null) {
            return Collections.emptyList();
        }

        return donors.stream()
                .filter(donor -> isCompatible(donor, recipient))
                .collect(Collectors.toList());
    }

    /**
     * Filters a list of recipients to return only those compatible with a donor.
     */
    public List<Recipient> getCompatibleRecipients(List<Recipient> recipients, Donor donor) {
        if (recipients == null || donor == null) {
            return Collections.emptyList();
        }

        return recipients.stream()
                .filter(recipient -> isCompatible(donor, recipient))
                .collect(Collectors.toList());
    }
}