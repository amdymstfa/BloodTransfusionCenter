package com.bloodtransfusioncenter.enums;

/**
 * Represents the urgency level for a blood transfusion.
 * <p>
 * CRITICAL: Immediate transfusion required (4 bags).
 * URGENT: Transfusion required soon (3 bags).
 * NORMAL: Transfusion can be scheduled normally (1 bag).
 */
public enum UrgencyLevel {
    CRITICAL(4),
    URGENT(3),
    NORMAL(1);

    private final int requiredBags;

    UrgencyLevel(int requiredBags) {
        this.requiredBags = requiredBags;
    }

    /**
     * Gets the number of blood bags required for this urgency level.
     * @return the number of required blood bags
     */
    public int getRequiredBags() {
        return requiredBags;
    }
}