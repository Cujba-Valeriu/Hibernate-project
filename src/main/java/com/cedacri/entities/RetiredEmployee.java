package com.cedacri.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "retired_employees")
public class RetiredEmployee extends Employee implements Serializable {
    private Boolean pension;
    private Integer yearsOfService;

    public RetiredEmployee() {
    }

    public RetiredEmployee(Boolean pension, Integer yearsOfService) {
        this.pension = pension;
        this.yearsOfService = yearsOfService;
    }

    public RetiredEmployee(String firstName, String lastName, Integer yearsExperience, List<Company> companies, EmployeeProfile profile, List<Salary> salaries, Boolean pension, Integer yearsOfService) {
        super(firstName, lastName, yearsExperience, companies, profile, salaries);
        this.pension = pension;
        this.yearsOfService = yearsOfService;
    }

    @Builder
    public RetiredEmployee(Long id, String firstName, String lastName, Integer yearsExperience, List<Company> companies, EmployeeProfile profile, List<Salary> salaries, Boolean pension, Integer yearsOfService) {
        super(id, firstName, lastName, yearsExperience, companies, profile, salaries);
        this.pension = pension;
        this.yearsOfService = yearsOfService;
    }
}
