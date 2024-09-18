package com.devteria.identity_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class TimeSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    String id;

    @CreatedDate
    @Column(name = "time_in")
    LocalDateTime timeIn;

    @Column(name = "time_out")
    LocalDateTime timeOut;

    @Column(name = "late")
    Long late;

    @Column(name = "work_hour")
    Long workHour;

    @ManyToOne
    Employee employee;
}
