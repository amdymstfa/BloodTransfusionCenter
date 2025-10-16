package com.bloodtransfusioncenter.enums;

public enum BloodType {

    O_POSITIVE("O+"),
    O_NEGATIVE("O-"),
    A_POSITIVE("A+"),
    A_NEGATIVE("A-"),
    B_POSITIVE("B+"),
    B_NEGATIVE("B-"),
    AB_POSITIVE("AB+"),
    AB_NEGATIVE("AB-");

    private final String label ;

    BloodType(String label){
        this.label = label ;
    }

    public String getLabel() {
        return label;
    }

    /*
     * Checks if this blood type is compatible with the recipient's blood type.
     * @param recipientBloodType the blood type of the recipient
     * @return true if the donor blood type can be safely transfused to the recipient, false otherwise.
     */
    public Boolean isCompatibleTo(BloodType recipient) {
        switch (this) {
            case O_NEGATIVE:
                return true;
            case O_POSITIVE:
                return recipient == O_POSITIVE || recipient == A_POSITIVE
                        || recipient == B_POSITIVE || recipient == AB_POSITIVE;
            case A_NEGATIVE:
                return recipient == A_NEGATIVE || recipient == A_POSITIVE
                        || recipient == AB_NEGATIVE || recipient == AB_POSITIVE;
            case A_POSITIVE:
                return recipient == A_POSITIVE || recipient == AB_POSITIVE;
            case B_NEGATIVE:
                return recipient == B_NEGATIVE || recipient == B_POSITIVE
                        || recipient == AB_NEGATIVE || recipient == AB_POSITIVE;
            case B_POSITIVE:
                return recipient == B_POSITIVE || recipient == AB_POSITIVE;
            case AB_NEGATIVE:
                return recipient == AB_NEGATIVE || recipient == AB_POSITIVE;
            case AB_POSITIVE:
                return recipient == AB_POSITIVE;
            default:
                return false;
        }
    }
}