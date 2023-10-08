package com.computerpool.library.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="student_details")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class StudentDetails {
    
    @Id
    @Column(name="details_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int detailsId;

    
    @Column(name="phone_number",unique = true)
    private long phoneNumber;

    @NotBlank
    @Column(name="address")
    private String address;

    @NotBlank
    @Column(name="gender")
    private String gender;

    @Min(1)
    @Max(8)
    @Column(name="no_of_semester")
    private int numberOfSemester;

    @NotBlank
    @Column(name="department")
    private String department;

    @NotBlank
    @Column(name="course_name")
    private String courseName;

    @NotBlank
    @Column(name="roles")
    private String roles;

    @NotBlank
    @Column(name="nationality")
    private String nationality;

    @NotBlank
    @Column(name="nfc_UUID",unique = true)
    private String nfcTagUUID;

    
    @Column(name="enabled")
    private Boolean enabled;

   
    @Column(name="activation_token")
    private String activationToken;

    @NotBlank
    @Column(name="password")
    private String password;

    @Column(name="two_factor_authentication")
    private String twoFactorAuthentication;

    @OneToOne(mappedBy = "studentDetails")
    private Student student;
}
