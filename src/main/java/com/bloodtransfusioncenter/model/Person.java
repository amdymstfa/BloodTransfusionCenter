package com.bloodtransfusioncenter.model;

import com.bloodtransfusioncenter.enums.BloodType;
import com.bloodtransfusioncenter.enums.Gender;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

/**
 * Abstract base class for Person entities (Donor and Recipient).
 * Contains common attributes shared between donors and recipients.
 */
@MappedSuperclass
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    protected String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    protected String lastName;

    @Column(nullable = false, length = 20)
    protected String phone;

    @Column(nullable = false, unique = true, length = 20)
    protected String cin;

    @Column(name = "birth_date", nullable = false)
    protected LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_type", nullable = false)
    protected BloodType bloodType;

    // Constructors
    public Person() {}

    public Person(String firstName, String lastName, String phone, String cin,
                  LocalDate birthDate, Gender gender, BloodType bloodType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.cin = cin;
        this.birthDate = birthDate;
        this.gender = gender;
        this.bloodType = bloodType;
    }

    // Business methods

    /**
     * Calculates the age of the person based on birth date.
     * @return the age in years
     */
    public int getAge() {
        if (birthDate == null) {
            return 0;
        }
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    /**
     * Returns the full name of the person.
     * @return firstName + lastName
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    // equals, hashCode, toString

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(cin, person.cin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cin);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", cin='" + cin + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", bloodType=" + bloodType +
                '}';
    }
}
