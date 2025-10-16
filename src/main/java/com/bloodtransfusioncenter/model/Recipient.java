package com.bloodtransfusioncenter.model;

import com.bloodtransfusioncenter.enums.BloodType;
import com.bloodtransfusioncenter.enums.Gender;
import com.bloodtransfusioncenter.enums.RecipientStatus;
import com.bloodtransfusioncenter.enums.UrgencyLevel;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a blood recipient in the system.
 * A recipient can receive multiple blood bags from different donors based on urgency level.
 */
@Entity
@Table(name = "recipients")
public class Recipient extends Person {

    @Enumerated(EnumType.STRING)
    @Column(name = "urgency_level", nullable = false)
    private UrgencyLevel urgencyLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecipientStatus status;

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Donor> donors = new ArrayList<>();

    // Constructors

    public Recipient() {
        super();
    }

    public Recipient(String firstName, String lastName, String phone, String cin,
                     LocalDate birthDate, Gender gender, BloodType bloodType,
                     UrgencyLevel urgencyLevel) {
        super(firstName, lastName, phone, cin, birthDate, gender, bloodType);
        this.urgencyLevel = urgencyLevel;
        this.status = RecipientStatus.WAITING;
    }

    // Business methods

    /**
     * Gets the number of blood bags required based on urgency level.
     * CRITICAL = 4 bags, URGENT = 3 bags, NORMAL = 1 bag
     *
     * @return the number of required bags
     */
    public int getRequiredBags() {
        return urgencyLevel.getRequiredBags();
    }

    /**
     * Gets the number of donors currently associated with this recipient.
     *
     * @return the count of associated donors
     */
    public int getAssociatedDonorsCount() {
        if (donors == null) {
            return 0;
        }
        try {
            return donors.size();
        } catch (org.hibernate.LazyInitializationException e) {
            return 0;
        }
    }

    /**
     * Checks if the recipient has received all required blood bags.
     *
     * @return true if satisfied (received all bags), false otherwise
     */
    public boolean isSatisfied() {
        return getAssociatedDonorsCount() >= getRequiredBags();
    }

    /**
     * Checks if the recipient can receive more donors.
     *
     * @return true if not yet satisfied, false otherwise
     */
    public boolean canReceiveMoreDonors() {
        return !isSatisfied();
    }

    /**
     * Adds a donor to this recipient.
     *
     * @param donor the donor to add
     */
    public void addDonor(Donor donor) {
        if (donor != null && canReceiveMoreDonors()) {
            donors.add(donor);
            donor.setRecipient(this);

            // Update status if satisfied
            if (isSatisfied()) {
                this.status = RecipientStatus.SATISFIED;
            }
        }
    }

    /**
     * Removes a donor from this recipient.
     *
     * @param donor the donor to remove
     */
    public void removeDonor(Donor donor) {
        if (donor != null) {
            donors.remove(donor);
            donor.setRecipient(null);

            // Update status back to WAITING if no longer satisfied
            if (!isSatisfied()) {
                this.status = RecipientStatus.WAITING;
            }
        }
    }

    // Getters and Setters

    public UrgencyLevel getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(UrgencyLevel urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public RecipientStatus getStatus() {
        return status;
    }

    public void setStatus(RecipientStatus status) {
        this.status = status;
    }

    public List<Donor> getDonors() {
        return donors;
    }

    public void setDonors(List<Donor> donors) {
        this.donors = donors;
    }

    @Override
    public String toString() {
        return "Recipient{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", bloodType=" + bloodType +
                ", urgencyLevel=" + urgencyLevel +
                ", status=" + status +
                ", donorsCount=" + getAssociatedDonorsCount() +
                ", requiredBags=" + getRequiredBags() +
                '}';
    }
}
