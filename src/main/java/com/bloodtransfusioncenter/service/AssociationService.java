package com.bloodtransfusioncenter.service;

import com.bloodtransfusioncenter.dao.DaoFactory;
import com.bloodtransfusioncenter.dao.DonorDao;
import com.bloodtransfusioncenter.dao.RecipientDao;
import com.bloodtransfusioncenter.model.Donor;
import com.bloodtransfusioncenter.model.Recipient;
import com.bloodtransfusioncenter.enums.DonorStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class to handle donor-recipient associations.
 * Manages the matching process based on compatibility and availability.
 */
public class AssociationService {

    private static final Logger logger = LoggerFactory.getLogger(AssociationService.class);
    private static AssociationService instance;

    private final DonorDao donorDao;
    private final RecipientDao recipientDao;
    private final CompatibilityService compatibilityService;

    private AssociationService() {
        this.donorDao = DaoFactory.getDonorDao();
        this.recipientDao = DaoFactory.getRecipientDao();
        this.compatibilityService = CompatibilityService.getInstance();
    }

    /**
     * Gets the singleton instance of AssociationService.
     */
    public static synchronized AssociationService getInstance() {
        if (instance == null) {
            instance = new AssociationService();
        }
        return instance;
    }

    /**
     * Associates a donor with a recipient.
     */
    public void associateDonorToRecipient(Long donorId, Long recipientId) throws Exception {
        logger.info("Associating donor {} with recipient {}", donorId, recipientId);

        // Validate IDs
        if (donorId == null || recipientId == null) {
            throw new Exception("Donor ID and Recipient ID are required");
        }

        // Fetch donor
        Optional<Donor> optionalDonor = donorDao.findById(donorId);
        if (!optionalDonor.isPresent()) {
            throw new Exception("Donor not found with ID: " + donorId);
        }
        Donor donor = optionalDonor.get();

        // Fetch recipient
        Optional<Recipient> optionalRecipient = recipientDao.findById(recipientId);
        if (!optionalRecipient.isPresent()) {
            throw new Exception("Recipient not found with ID: " + recipientId);
        }
        Recipient recipient = optionalRecipient.get();

        // Validate association
        validateAssociation(donor, recipient);

        // Perform association
        donor.setRecipient(recipient);
        donor.setStatus(DonorStatus.NOT_AVAILABLE);
        recipient.addDonor(donor);

        // Update in database
        donorDao.update(donor);
        recipientDao.update(recipient);

        logger.info("Association successful. Recipient status: {}", recipient.getStatus());
    }

    /**
     * Removes association between donor and recipient.
     */
    public void removeAssociation(Long donorId) throws Exception {
        logger.info("Removing association for donor {}", donorId);

        if (donorId == null) {
            throw new Exception("Donor ID is required");
        }

        Optional<Donor> optionalDonor = donorDao.findById(donorId);
        if (!optionalDonor.isPresent()) {
            throw new Exception("Donor not found");
        }

        Donor donor = optionalDonor.get();
        Recipient recipient = donor.getRecipient();

        if (recipient == null) {
            throw new Exception("Donor is not associated with any recipient");
        }

        // Remove association
        recipient.removeDonor(donor);
        donor.setRecipient(null);
        donor.setStatus(DonorStatus.AVAILABLE);

        // Update in database
        donorDao.update(donor);
        recipientDao.update(recipient);

        logger.info("Association removed successfully");
    }

    /**
     * Gets available donors compatible with a recipient.
     */
    public List<Donor> getCompatibleAvailableDonors(Long recipientId) throws Exception {
        if (recipientId == null) {
            throw new Exception("Recipient ID is required");
        }

        Optional<Recipient> optionalRecipient = recipientDao.findById(recipientId);
        if (!optionalRecipient.isPresent()) {
            throw new Exception("Recipient not found");
        }

        Recipient recipient = optionalRecipient.get();
        List<Donor> availableDonors = donorDao.findAvailable();

        return compatibilityService.getCompatibleDonors(availableDonors, recipient);
    }

    /**
     * Gets waiting recipients compatible with a donor.
     */
    public List<Recipient> getCompatibleWaitingRecipients(Long donorId) throws Exception {
        if (donorId == null) {
            throw new Exception("Donor ID is required");
        }

        Optional<Donor> optionalDonor = donorDao.findById(donorId);
        if (!optionalDonor.isPresent()) {
            throw new Exception("Donor not found");
        }

        Donor donor = optionalDonor.get();
        List<Recipient> waitingRecipients = recipientDao.findWaiting();

        return compatibilityService.getCompatibleRecipients(waitingRecipients, donor);
    }

    /**
     * Validates if a donor can be associated with a recipient.
     */
    private void validateAssociation(Donor donor, Recipient recipient) throws Exception {
        // Check if donor is available
        if (donor.getStatus() != DonorStatus.AVAILABLE) {
            throw new Exception("Donor is not available for donation");
        }

        // Check if donor is already associated
        if (donor.getRecipient() != null) {
            throw new Exception("Donor is already associated with a recipient");
        }

        // Check if recipient can receive more donors
        if (!recipient.canReceiveMoreDonors()) {
            throw new Exception("Recipient has already received all required blood bags");
        }

        // Check blood type compatibility
        if (!compatibilityService.isCompatible(donor, recipient)) {
            throw new Exception("Blood types are not compatible");
        }
    }
}
