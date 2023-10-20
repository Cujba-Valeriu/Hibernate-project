package com.cedacri.repositories.impl;

import com.cedacri.entities.*;
import com.cedacri.repositories.EmployeeRepository;
import com.cedacri.repositories.SalaryRepository;
import com.cedacri.utils.PersistenceManagerTestUtil;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SalaryRepositoryImplTest {


    static SalaryRepository repository;
    static EntityManager entityManager;

    @BeforeAll
    static void init() {
        entityManager = PersistenceManagerTestUtil.getEntityManger();
        repository = new SalaryRepositoryImpl(entityManager);
    }

    @AfterAll
    static void close() {
        entityManager.close();
        PersistenceManagerTestUtil.closeEntityManagerFactory();
    }

    @BeforeEach
    void cleanDB() {
        List<Salary> salaries = repository.getAllSalaries();
        for (Salary salary : salaries) {
            repository.deleteSalary(salary);
        }
    }


    @Test
    void saveSalary_ShouldNotFail() {
        Salary salary = Salary.builder()
                .currentSalary(2000.00)
                .activeFlag(true)
                .company(Company.builder()
                        .name("Cedacri")
                        .city("Chisinau")
                        .zipcode("200-MD")
                        .build())
                .employee(ActiveEmployee.builder()
                        .firstName("Omar")
                        .lastName("Ion")
                        .yearsExperience(5)
                        .build())
                .build();


        Salary saved = repository.saveOrUpdateSalary(salary).get();

        assertNotNull(saved);
        assertEquals(2000.00, saved.getCurrentSalary(), 0.1);
    }

    @Test
    void updateSalary_ShouldNotFail() {
        Salary salary = Salary.builder()
                .currentSalary(2000.00)
                .activeFlag(true)
                .company(Company.builder()
                        .name("Cedacri")
                        .city("Chisinau")
                        .zipcode("200-MD")
                        .build())
                .employee(ActiveEmployee.builder()
                        .firstName("Omar")
                        .lastName("Ion")
                        .yearsExperience(5)
                        .build())
                .build();


        Salary saved = repository.saveOrUpdateSalary(salary).get();

        assertNotNull(saved);
        assertEquals(2000.00, saved.getCurrentSalary(), 0.1);

        saved.setCurrentSalary(2500.00);
        saved.setCompany(Company.builder()
                .name("International")
                .city("Chisinau")
                .zipcode("200-MD")
                .build());

        Salary updated = repository.saveOrUpdateSalary(salary).get();
        assertNotNull(saved);
        assertEquals(2500.00, updated.getCurrentSalary(), 0.1);
        assertEquals("International", updated.getCompany().getName());
    }

    @Test
    void deleteSalary_ShouldNotFail() {
        Salary salary = Salary.builder()
                .currentSalary(2000.00)
                .activeFlag(true)
                .company(Company.builder()
                        .name("Cedacri")
                        .city("Chisinau")
                        .zipcode("200-MD")
                        .build())
                .employee(ActiveEmployee.builder()
                        .firstName("Omar")
                        .lastName("Ion")
                        .yearsExperience(5)
                        .build())
                .build();


        Salary saved = repository.saveOrUpdateSalary(salary).get();

        assertNotNull(saved);
        assertEquals(2000.00, saved.getCurrentSalary(), 0.1);

        repository.deleteSalary(saved);

        assertEquals(0, repository.getAllSalaries().size());

    }

    @Test
    void getSalaryById_ShouldNotFail() {
        Salary salary = Salary.builder()
                .currentSalary(2000.00)
                .activeFlag(true)
                .company(Company.builder()
                        .name("Cedacri")
                        .city("Chisinau")
                        .zipcode("200-MD")
                        .build())
                .employee(RetiredEmployee.builder()
                        .firstName("Omar")
                        .lastName("Ion")
                        .yearsExperience(5)
                        .build())
                .build();


        Salary saved = repository.saveOrUpdateSalary(salary).get();

        assertNotNull(saved);
        assertEquals(2000.00, saved.getCurrentSalary(), 0.1);

        Salary found = repository.getSalaryById(salary.getId()).get();

        assertEquals(salary.getActiveFlag(), found.getActiveFlag());
        assertEquals(salary.getCurrentSalary(), found.getCurrentSalary());
    }

    @Test
    void getAllSalaries_ShouldNotFail() {
        Salary salary = Salary.builder()
                .currentSalary(2000.00)
                .activeFlag(true)
                .company(Company.builder()
                        .name("Cedacri")
                        .city("Chisinau")
                        .zipcode("200-MD")
                        .build())
                .employee(ActiveEmployee.builder()
                        .firstName("Omar")
                        .lastName("Ion")
                        .yearsExperience(5)
                        .build())
                .build();

        Salary salary1 = Salary.builder()
                .currentSalary(1000.00)
                .activeFlag(false)
                .company(Company.builder()
                        .name("Cedacri")
                        .city("Chisinau")
                        .zipcode("200-MD")
                        .build())
                .employee(RetiredEmployee.builder()
                        .firstName("Vasile")
                        .lastName("Ciubotaru")
                        .yearsExperience(10)
                        .build())
                .build();


        Salary saved = repository.saveOrUpdateSalary(salary).get();
        Salary saved1 = repository.saveOrUpdateSalary(salary1).get();

        assertNotNull(saved);
        assertNotNull(saved1);
        assertEquals(2000.00, saved.getCurrentSalary(), 0.1);
        assertEquals(1000.00, saved1.getCurrentSalary(), 0.1);


        List<Salary> salaries = repository.getAllSalaries();

        assertNotNull(salaries);
        assertEquals(2, salaries.size());
    }

    @Test
    void getSalariesByActiveFlag_ShouldNotFail() {
        Salary salary = Salary.builder()
                .currentSalary(2000.00)
                .activeFlag(true)
                .company(Company.builder()
                        .name("Cedacri")
                        .city("Chisinau")
                        .zipcode("200-MD")
                        .build())
                .employee(ActiveEmployee.builder()
                        .firstName("Omar")
                        .lastName("Ion")
                        .yearsExperience(5)
                        .build())
                .build();

        Salary salary1 = Salary.builder()
                .currentSalary(1000.00)
                .activeFlag(false)
                .company(Company.builder()
                        .name("Cedacri")
                        .city("Chisinau")
                        .zipcode("200-MD")
                        .build())
                .employee(RetiredEmployee.builder()
                        .firstName("Vasile")
                        .lastName("Ciubotaru")
                        .yearsExperience(10)
                        .build())
                .build();


        Salary saved = repository.saveOrUpdateSalary(salary).get();
        Salary saved1 = repository.saveOrUpdateSalary(salary1).get();

        assertNotNull(saved);
        assertNotNull(saved1);
        assertEquals(2000.00, saved.getCurrentSalary(), 0.1);
        assertEquals(1000.00, saved1.getCurrentSalary(), 0.1);


        List<Salary> salaries = repository.getSalariesByActiveFlag(false);

        assertNotNull(salaries);
        assertEquals(1, salaries.size());
    }
}