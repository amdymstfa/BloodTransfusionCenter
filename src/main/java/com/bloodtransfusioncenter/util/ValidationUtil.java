package com.bloodtransfusioncenter.util ;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * Utility class for common validation operations.
 * Provides reusable validation methods for various data types.
 */
public class ValidationUtil {

    // Regex patterns
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(\\+212|0)[5-7]\\d{8}$");
    private static final Pattern CIN_PATTERN = Pattern.compile("^[A-Z]{1,2}\\d{5,7}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-zÀ-ÿ\\s'-]{2,50}$");

    // Private constructor to prevent instantiation
    private ValidationUtil() {}

    /**
     * Validates a name (first name or last name).
     * Must be 2-50 characters, letters only (including accents).
     *
     * @param name the name to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return NAME_PATTERN.matcher(name.trim()).matches();
    }

    /**
     * Validates a Moroccan phone number.
     * Formats accepted: 0612345678, +212612345678, 0712345678, 0512345678
     *
     * @param phone the phone number to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    /**
     * Validates a Moroccan CIN (Carte d'Identité Nationale).
     * Format: 1-2 letters followed by 5-7 digits (e.g., AB123456, K123456)
     *
     * @param cin the CIN to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidCIN(String cin) {
        if (cin == null || cin.trim().isEmpty()) {
            return false;
        }
        return CIN_PATTERN.matcher(cin.trim().toUpperCase()).matches();
    }

    /**
     * Validates an email address.
     *
     * @param email the email to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Validates a date is not in the future.
     *
     * @param date the date to validate
     * @return true if date is today or in the past, false otherwise
     */
    public static boolean isValidDate(LocalDate date) {
        if (date == null) {
            return false;
        }
        return !date.isAfter(LocalDate.now());
    }

    /**
     * Validates that a date is within a specific age range.
     *
     * @param birthDate the birth date to validate
     * @param minAge minimum age required
     * @param maxAge maximum age allowed
     * @return true if age is within range, false otherwise
     */
    public static boolean isValidAge(LocalDate birthDate, int minAge, int maxAge) {
        if (birthDate == null) {
            return false;
        }

        LocalDate now = LocalDate.now();
        LocalDate minBirthDate = now.minusYears(maxAge);
        LocalDate maxBirthDate = now.minusYears(minAge);

        return !birthDate.isBefore(minBirthDate) && !birthDate.isAfter(maxBirthDate);
    }

    /**
     * Validates a weight value.
     *
     * @param weight the weight to validate
     * @param minWeight minimum weight allowed
     * @return true if weight is valid and above minimum, false otherwise
     */
    public static boolean isValidWeight(Double weight, double minWeight) {
        if (weight == null) {
            return false;
        }
        return weight >= minWeight && weight <= 300.0; // Max 300kg
    }

    /**
     * Validates that a string is not null or empty.
     *
     * @param value the string to validate
     * @return true if not null and not empty (after trim), false otherwise
     */
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * Validates that a string length is within range.
     *
     * @param value the string to validate
     * @param minLength minimum length
     * @param maxLength maximum length
     * @return true if length is within range, false otherwise
     */
    public static boolean isValidLength(String value, int minLength, int maxLength) {
        if (value == null) {
            return false;
        }
        int length = value.trim().length();
        return length >= minLength && length <= maxLength;
    }

    /**
     * Sanitizes a string by trimming whitespace.
     *
     * @param value the string to sanitize
     * @return the sanitized string, or null if input was null
     */
    public static String sanitize(String value) {
        return value != null ? value.trim() : null;
    }
}