package com.cedacri.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "employees")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private Integer yearsExperience;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "employee_company",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    private List<Company> companies = new ArrayList<>();


    @OneToOne(cascade = CascadeType.ALL)
    private EmployeeProfile profile;

    @Column
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "employee_id")
    private List<Salary> salaries = new ArrayList<>();

    public Employee() {
    }

    public Employee(String firstName, String lastName, Integer yearsExperience, List<Company> companies, EmployeeProfile profile, List<Salary> salaries) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearsExperience = yearsExperience;
        this.companies = companies;
        this.profile = profile;
        this.salaries = salaries;
    }

    public Employee(Long id, String firstName, String lastName, Integer yearsExperience, List<Company> companies, EmployeeProfile profile, List<Salary> salaries) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearsExperience = yearsExperience;
        this.companies = companies;
        this.profile = profile;
        this.salaries = salaries;
    }


}
