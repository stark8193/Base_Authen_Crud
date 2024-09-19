package com.devteria.identity_service.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "department")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Department implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dep_id")
    private Long depID;

    @Column(name = "dep_name", length = 255, nullable = false)
    private String depName;

    @Column(name = "dep_short_name", length = 3, nullable = false)
    private String depShortName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manage_id", nullable = true)
    private Employee manageID;


    public Department() {}

    public Long getDepID() {
        return depID;
    }

    public void setDepID(Long depID) {
        this.depID = depID;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getDepShortName() {
        return depShortName;
    }

    public void setDepShortName(String depShortName) {
        this.depShortName = depShortName;
    }

    public Employee getManageID() {
        return manageID;
    }

    public void setManageID(Employee manageID) {
        this.manageID = manageID;
    }
}
