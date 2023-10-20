package com.cedacri.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;


@Getter
@Setter
@Entity
@Table(name = "active_employees")
public class ActiveEmployee extends Employee implements Serializable {

    @Column
    private Date startDate;
    @Transient
    private Double totalCompensation;

    public ActiveEmployee() {
    }

    public ActiveEmployee(Date startDate, Double totalCompensation) {
        this.startDate = startDate;
        this.totalCompensation = totalCompensation;
    }

    @Builder
    public ActiveEmployee(Long id, String firstName, String lastName, Integer yearsExperience, List<Company> companies, EmployeeProfile profile, List<Salary> salaries, Date startDate, Double totalCompensation) {
        super(id, firstName, lastName, yearsExperience, companies, profile, salaries);
        this.startDate = startDate;
        this.totalCompensation = totalCompensation;
    }

    public ActiveEmployee(String firstName, String lastName, Integer yearsExperience, List<Company> companies, EmployeeProfile profile, List<Salary> salaries, Date startDate, Double totalCompensation) {
        super(firstName, lastName, yearsExperience, companies, profile, salaries);
        this.startDate = startDate;
        this.totalCompensation = totalCompensation;
    }



}
