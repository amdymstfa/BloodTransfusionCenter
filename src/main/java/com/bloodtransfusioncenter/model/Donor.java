package com.bloodtransfusioncenter.model;

import com.bloodtransfusioncenter.enums.BloodType;
import com.bloodtransfusioncenter.enums.DonorStatus;
import com.bloodtransfusioncenter.enums.Gender;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Represents a blood donor in the system.
 * A donor can donate one blood bag to one recipient at a time.
 */
@Entity
@Table(name = "donors")
public class Donor extends Person {

    @Column(nullable = false)
    private Double weight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DonorStatus status;

    @Column(name = "contraindications", columnDefinition = "TEXT")
    private String contraindications;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private Recipient recipient;

    // Constructors

    public Donor() {
        super();
    }

    public Donor(String firstName, String lastName, String phone, String cin,
                 LocalDate birthDate, Gender gender, BloodType bloodType,
                 Double weight, String contraindications) {
        super(firstName, lastName, phone, cin, birthDate, gender, bloodType);
        this.weight = weight;
        this.contraindications = contraindications;
        this.status = DonorStatus.AVAILABLE; // Default status
    }

    // Business methods

    /**
     * Checks if the donor is eligible to donate blood.
     * Eligibility criteria:
     * - Age between 18 and 65
     * - Weight at least 50 kg
     * - No medical contraindications
     *
     * @return true if eligible, false otherwise
     */
    public boolean isEligible() {
        int age = getAge();

        // Check age (18-65)
        if (age < 18 || age > 65) {
            return false;
        }

        // Check weight (>= 50kg)
        if (weight == null || weight < 50.0) {
            return false;
        }

        // Check contraindications
        if (hasContraindications()) {
            return false;
        }

        return true;
    }

    /**
     * Checks if the donor has medical contraindications.
     * Contraindications: hepatitis B/C, HIV, insulin-dependent diabetes, pregnancy, breastfeeding
     *
     * @return true if contraindications exist, false otherwise
     */
    public boolean hasContraindications() {
        if (contraindications == null || contraindications.trim().isEmpty()) {
            return false;
        }

        String ci = contraindications.toLowerCase();
        return ci.contains("hepatitis") || ci.contains("hepatite") ||
                ci.contains("hiv") || ci.contains("vih") ||
                ci.contains("diabetes") || ci.contains("diabete") ||
                ci.contains("pregnancy") || ci.contains("grossesse") ||
                ci.contains("breastfeeding") || ci.contains("allaitement");
    }

    /**
     * Checks if the donor is currently available to donate.
     *
     * @return true if status is AVAILABLE, false otherwise
     */
    public boolean isAvailable() {
        return status == DonorStatus.AVAILABLE;
    }

    // Getters and Setters

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public DonorStatus getStatus() {
        return status;
    }

    public void setStatus(DonorStatus status) {
        this.status = status;
    }

    public String getContraindications() {
        return contraindications;
    }

    public void setContraindications(String contraindications) {
        this.contraindications = contraindications;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    @Override
    public String toString() {
        return "Donor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", bloodType=" + bloodType +
                ", weight=" + weight +
                ", status=" + status +
                ", recipientId=" + (recipient != null ? recipient.getId() : null) +
                '}';
    }
}
