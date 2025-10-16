package com.bloodtransfusioncenter.enums;

/**
 * Represents the current status of a blood donor.
 * <p>
 * AVAILABLE: The donor is eligible and ready to donate blood.
 * NOT_AVAILABLE: The donor is temporarily unavailable to donate.
 * NOT_ELIGIBLE: The donor is permanently or temporarily not eligible to donate.
 */
public enum DonorStatus {
    AVAILABLE,
    NOT_AVAILABLE,
    NOT_ELIGIBLE;
}
