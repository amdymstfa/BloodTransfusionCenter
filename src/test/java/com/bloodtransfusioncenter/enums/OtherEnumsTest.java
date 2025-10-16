package com.bloodtransfusioncenter.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OtherEnumsTest {

    @Test
    void testDonorStatusValues() {
        assertEquals(3, DonorStatus.values().length);
        assertTrue(DonorStatus.AVAILABLE.name().equals("AVAILABLE"));
    }

    @Test
    void testRecipientStatusValues() {
        assertEquals(2, RecipientStatus.values().length);
    }

    @Test
    void testGenderValues() {
        assertEquals(2, Gender.values().length);
    }

    @Test
    void testUrgencyLevelValues() {
        assertEquals(3, UrgencyLevel.values().length);
    }
}
