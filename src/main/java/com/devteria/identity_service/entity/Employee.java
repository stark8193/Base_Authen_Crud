package com.devteria.identity_service.entity;

import com.devteria.identity_service.entity.enumeration.EmpStatus;
import com.devteria.identity_service.entity.enumeration.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.mapping.ToOne;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "employee")
public class Employee{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    String id;

    @Column(name = "employee_name")
    String employeeName;

    @Column(name = "birth_date")
    LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    Gender gender;

    @Column(name = "hire_date")
    LocalDate hireDate;

    @Column(name = "email")
    String email;

    @Column(name = "phone")
    Long phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_status")
    EmpStatus employeeStatus;

    @Column(name = "tax_code")
    Long taxCode;

    @Column(name = "cccd")
    Long cccd;

    @Column(name = "address")
    String address;

    @Column(name = "bank_account_number")
    Long bankAccountNumber;

    @Column(name = "bank")
    String bank;

    @Column(name = "bank_branch")
    String bankBranch;

    @Column(name = "photo_path")
    String photoPath;

    @ManyToOne
    Role role;
}
