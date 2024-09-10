//package com.devteria.identity_service.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//
//import javax.persistence.*;
//import javax.validation.constraints.*;
//import java.io.Serializable;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * A Department.
// */
//@Entity
//@Table(name = "department")
//@SuppressWarnings("common-java:DuplicatedBlocks")
//public class Department implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @NotNull
//    @Column(name = "depart_name", nullable = false)
//    private String departName;
//
//    @Column(name = "short_name")
//    private String shortName;
//}
