package com.bloodtransfusioncenter.enums;

/**
 * Represents the urgency level for a blood transfusion.
 * <p>
 * CRITICAL: Immediate transfusion required.
 * URGENT: Transfusion required soon.
 * NORMAL: Transfusion can be scheduled normally.
 */
public enum UrgencyLevel {
    CRITICAL(4),
    URGENT(3),
    NORMAL(1);

    private final int requiredBags ;

    UrgencyLevel(int requiredBags) {
        this.requiredBags = requiredBags;
    }

    /*
    * Gets the number of blood bags required for this urgency level.
    * @return the number of required blood bags
     */

    public int getRequiredBags() {
        return requiredBags;
    }
}
