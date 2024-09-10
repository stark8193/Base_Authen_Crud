package com.devteria.identity_service.configuration;

import com.devteria.identity_service.constant.PredefinedRole;
import com.devteria.identity_service.entity.Employee;
import com.devteria.identity_service.entity.Role;
import com.devteria.identity_service.entity.User;
import com.devteria.identity_service.entity.enumeration.EmpStatus;
import com.devteria.identity_service.entity.enumeration.Gender;
import com.devteria.identity_service.entity.enumeration.UserStatus;
import com.devteria.identity_service.repository.EmployeeRepository;
import com.devteria.identity_service.repository.RoleRepository;
import com.devteria.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;
    @NonFinal
    static final String ADMIN_EMPLOYEE = "admin";

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository, EmployeeRepository employeeRepository) {
        log.info("Initializing application.....");
        return args -> {
            if (userRepository.findByUsername(ADMIN_USER_NAME).isEmpty()) {
                Role userRole = roleRepository.save(Role.builder()
                        .role_name(PredefinedRole.USER_ROLE)
                        .build());

                Role hrmRole = roleRepository.save(Role.builder()
                        .role_name(PredefinedRole.HRM_ROLE)
                        .build());

                Role adminRole = roleRepository.save(Role.builder()
                        .role_name(PredefinedRole.ADMIN_ROLE)
                        .build());

                Employee employee = Employee.builder()
                        .employeeName(ADMIN_EMPLOYEE)
                        .birthDate(LocalDate.of(2003,8,1))
                        .gender(Gender.MALE)
                        .hireDate(LocalDate.of(2024,1,1))
                        .phone(9238475283L)
                        .employeeStatus(EmpStatus.FULLTIME)
                        .taxCode(1L)
                        .cccd(1L)
                        .address("HA NOI")
                        .bank("BIDV")
                        .bankBranch("BIDV")
                        .bankAccountNumber(1L)
                        .email("thangngudan@gmail.com")
                        .photoPath("https://i.ibb.co/G2f8BXB/1643102019-mochi-phucbontu-400x400.jpg")
                        .role(adminRole)
                        .build();
                employee = employeeRepository.save(employee);

                User user = User.builder()
                        .employee(employee)
                        .username(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .userStatus(UserStatus.ACTIVE)
                        .build();
                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }
}
