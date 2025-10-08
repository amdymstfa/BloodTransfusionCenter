package com.bloodtransfusioncenter.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BloodTypeTest {

    @Test
    void testCompatibility_O_Positive_To_A_Positive() {
        assertTrue(BloodType.O_POSITIVE.isCompatibleTo(BloodType.A_POSITIVE));
    }

    @Test
    void testCompatibility_A_Positive_To_B_Positive() {
        assertFalse(BloodType.A_POSITIVE.isCompatibleTo(BloodType.B_POSITIVE));
    }

    @Test
    void testCompatibility_AB_Positive_To_AB_Positive() {
        assertTrue(BloodType.AB_POSITIVE.isCompatibleTo(BloodType.AB_POSITIVE));
    }

    @Test
    void testCompatibility_O_Negative_To_All() {
        for (BloodType recipient : BloodType.values()) {
            assertTrue(BloodType.O_NEGATIVE.isCompatibleTo(recipient),
                    "O- should be compatible with " + recipient);
        }
    }
}
