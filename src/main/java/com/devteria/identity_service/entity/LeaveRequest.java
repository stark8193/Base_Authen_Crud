package com.devteria.identity_service.entity;

import com.devteria.identity_service.entity.enumeration.LeaveRequestStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "leave_request")
public class LeaveRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LeaveRequestStatus status;

    @Column(name = "reason")
    private String reason;

    @ManyToOne
    Employee employee;

    @ManyToOne
    LeaveType leaveType;
}
