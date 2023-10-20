package com.cedacri.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Builder
@Getter
@Setter
@Entity
@Table(name = "employee_profile")
public class EmployeeProfile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;
    @Column
    private String userName;
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private String title;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public EmployeeProfile() {
    }

    public EmployeeProfile(String userName, String password, String email, String title, Employee employee) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.title = title;
        this.employee = employee;
    }

    public EmployeeProfile(Long id, String userName, String password, String email, String title, Employee employee) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.title = title;
        this.employee = employee;
    }

}
