package com.bloodtransfusioncenter.dao;

import com.bloodtransfusioncenter.model.Donor;
import com.bloodtransfusioncenter.enums.BloodType;
import com.bloodtransfusioncenter.enums.DonorStatus;

import java.util.List;

/**
 * DAO interface for Donor entity with specific query methods.
 */
public interface DonorDao extends GenericDao<Donor> {

    /**
     * Finds all donors with a specific blood type.
     * @param bloodType the blood type to search for
     * @return list of donors with the specified blood type
     */
    List<Donor> findByBloodType(BloodType bloodType);

    /**
     * Finds all available donors.
     * @return list of available donors
     */
    List<Donor> findAvailable();

    /**
     * Finds all donors with a specific status.
     * @param status the donor status
     * @return list of donors with the specified status
     */
    List<Donor> findByStatus(DonorStatus status);
}