package com.cedacri.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@Entity
@Table(name = "companies")
public class Company implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;
    @Column
    @NaturalId
    private String name;
    @Column
    private String city;
    @Column
    private String state;
    @Column
    private String zipcode;
    @Column
    private String country;

    @Column
    @ManyToMany(mappedBy = "companies")
    private List<Employee> employees = new ArrayList<>();

    public Company() {
    }

    public Company(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public Company(Long id, String name, String city, String state, String zipcode, String country, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.country = country;
        this.employees = employees;
    }

}
