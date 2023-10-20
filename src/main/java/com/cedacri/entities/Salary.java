package com.cedacri.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@Entity
@Table(name = "salaries")
public class Salary implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Company company;
    @Column
    private Integer level;
    @Column
    private Integer bonusPercentage;
    @Column
    private Double startingSalary;
    @Column
    private Double currentSalary;
    @Column
    private Boolean activeFlag;
    @Column
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Employee employee;

    public Salary() {

    }

    public Salary(Double currentSalary, Boolean activeFlag) {
        this.currentSalary = currentSalary;
        this.activeFlag = activeFlag;
    }

    public Salary(Long id, Company company, Integer level, Integer bonusPercentage, Double startingSalary, Double currentSalary, Boolean activeFlag, String title, Employee employee) {
        this.id = id;
        this.company = company;
        this.level = level;
        this.bonusPercentage = bonusPercentage;
        this.startingSalary = startingSalary;
        this.currentSalary = currentSalary;
        this.activeFlag = activeFlag;
        this.title = title;
        this.employee = employee;
    }
}
