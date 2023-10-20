package com.cedacri.repositories.impl;

import com.cedacri.entities.ActiveEmployee;
import com.cedacri.entities.Employee;
import com.cedacri.entities.RetiredEmployee;
import com.cedacri.entities.Salary;
import com.cedacri.repositories.EmployeeRepository;
import com.cedacri.utils.PersistenceManagerTestUtil;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeRepositoryImplTest {

    static EmployeeRepository repository;
    static EntityManager entityManager;

    @BeforeAll
    static void init() {
        entityManager = PersistenceManagerTestUtil.getEntityManger();
        repository = new EmployeeRepositoryImpl(entityManager);
    }

    @AfterAll
    static void close() {
        PersistenceManagerTestUtil.closeEntityManagerFactory();
    }

    @BeforeEach
    void cleanDB() {
        List<Employee> companies = repository.getAllEmployees();
        for (Employee employee : companies) {
            repository.deleteEmployee(employee);
        }
    }


    @Test
    void saveEmployee_ShouldNotFail() {
        Employee employee = ActiveEmployee.builder()
                .firstName("Ion")
                .lastName("Popescu")
                .yearsExperience(5)
                .salaries(Arrays.asList(Salary.builder().activeFlag(true).currentSalary(2000.00).bonusPercentage(20).build()))
                .build();

        Employee saved = repository.saveOrUpdateEmployee(employee).get();
        assertEquals("Ion", saved.getFirstName());
        assertEquals(5, saved.getYearsExperience());

    }

    @Test
    void updateEmployee_ShouldNotFail() {
        Employee employee = ActiveEmployee.builder()
                .firstName("Ion")
                .lastName("Popescu")
                .yearsExperience(5)
                .salaries(Stream.of(Salary.builder().activeFlag(true).currentSalary(2000.00).bonusPercentage(20).build()).collect(Collectors.toList()))
                .build();

        Employee saved = repository.saveOrUpdateEmployee(employee).get();
        assertEquals("Ion", saved.getFirstName());
        assertEquals(5, saved.getYearsExperience());

        saved.setYearsExperience(10);
        saved.setFirstName("Vasile");

        Employee updated = repository.saveOrUpdateEmployee(saved).get();
        assertEquals("Vasile", updated.getFirstName());
        assertEquals(10, saved.getYearsExperience());


    }


    @Test
    void deleteEmployee() {
        Employee employee = ActiveEmployee.builder()
                .firstName("Ion")
                .lastName("Popescu")
                .yearsExperience(5)
                .salaries(Arrays.asList(Salary.builder().activeFlag(true).currentSalary(2000.00).bonusPercentage(20).build()))
                .build();

        Employee saved = repository.saveOrUpdateEmployee(employee).get();
        assertEquals("Ion", saved.getFirstName());

        repository.deleteEmployee(employee);
        assertEquals(0, repository.getAllEmployees().size());
    }

    @Test
    void getEmployeeById_ShouldNotFail() {
        Employee employee = ActiveEmployee.builder()
                .firstName("Ion")
                .lastName("Popescu")
                .yearsExperience(5)
                .salaries(Arrays.asList(Salary.builder().activeFlag(true).currentSalary(2000.00).bonusPercentage(20).build()))
                .build();

        Employee saved = repository.saveOrUpdateEmployee(employee).get();
        assertEquals("Ion", saved.getFirstName());

        Employee found = repository.getEmployeeById(saved.getId()).get();
        assertEquals(saved.getFirstName(), found.getFirstName());
        assertEquals(saved.getLastName(), found.getLastName());

    }

    @Test
    void getEmployeesByExperienceCriteriaQuery_ShouldNotFail() {
        Employee employee = ActiveEmployee.builder()
                .firstName("Ion")
                .lastName("Popescu")
                .yearsExperience(9)
                .salaries(Arrays.asList(Salary.builder().activeFlag(true).currentSalary(2000.00).bonusPercentage(20).build()))
                .build();

        Employee employee1 = RetiredEmployee.builder()
                .firstName("Ion")
                .lastName("Popescu")
                .yearsExperience(10)
                .salaries(Arrays.asList(Salary.builder().activeFlag(true).currentSalary(2000.00).bonusPercentage(20).build()))
                .build();
        Employee saved = repository.saveOrUpdateEmployee(employee).get();
        Employee saved1 = repository.saveOrUpdateEmployee(employee1).get();
        assertEquals(9, saved.getYearsExperience());
        assertEquals(10, saved1.getYearsExperience());

        List<Employee> found = repository.getEmployeesByExperienceCriteriaQuery(5);

        assertNotNull(found);
        assertEquals(2, found.size());

    }

    @Test
    void getEmployeeByFullName_ShouldNotFail() {
        Employee employee = ActiveEmployee.builder()
                .firstName("Ion")
                .lastName("Popescu")
                .yearsExperience(5)
                .salaries(Arrays.asList(Salary.builder().activeFlag(true).currentSalary(2000.00).bonusPercentage(20).build()))
                .build();

        Employee saved = repository.saveOrUpdateEmployee(employee).get();
        assertEquals("Ion", saved.getFirstName());

        Employee found = repository.getEmployeeByFullName(saved.getFirstName(), saved.getLastName()).get();

        assertNotNull(found);
        assertEquals(saved.getYearsExperience(), found.getYearsExperience());

    }

    @Test
    void getEmployeesByExperienceJpqlQuery_ShouldNotFail() {

        Employee employee = ActiveEmployee.builder()
                .firstName("Ion")
                .lastName("Popescu")
                .yearsExperience(9)
                .salaries(Arrays.asList(Salary.builder().activeFlag(true).currentSalary(2000.00).bonusPercentage(20).build()))
                .build();

        Employee employee1 = RetiredEmployee.builder()
                .firstName("Ion")
                .lastName("Popescu")
                .yearsExperience(10)
                .salaries(Arrays.asList(Salary.builder().activeFlag(true).currentSalary(2000.00).bonusPercentage(20).build()))
                .build();
        Employee saved = repository.saveOrUpdateEmployee(employee).get();
        Employee saved1 = repository.saveOrUpdateEmployee(employee1).get();
        assertEquals(9, saved.getYearsExperience());
        assertEquals(10, saved1.getYearsExperience());

        List<Employee> found = repository.getEmployeesByExperienceJpqlQuery(5);

        assertNotNull(found);
        assertEquals(2, found.size());
    }

    @Test
    void getEmployeesByExperienceNativeQuery_ShouldNotFail() {
        Employee employee = ActiveEmployee.builder()
                .firstName("Ion")
                .lastName("Popescu")
                .yearsExperience(9)
                .salaries(Arrays.asList(Salary.builder().activeFlag(true).currentSalary(2000.00).bonusPercentage(20).build()))
                .build();

        Employee employee1 = RetiredEmployee.builder()
                .firstName("Ion")
                .lastName("Popescu")
                .yearsExperience(10)
                .salaries(Arrays.asList(Salary.builder().activeFlag(true).currentSalary(2000.00).bonusPercentage(20).build()))
                .build();
        Employee saved = repository.saveOrUpdateEmployee(employee).get();
        Employee saved1 = repository.saveOrUpdateEmployee(employee1).get();
        assertEquals(9, saved.getYearsExperience());
        assertEquals(10, saved1.getYearsExperience());

        List<Employee> found = repository.getEmployeesByExperienceNativeQuery(5);

        assertNotNull(found);
        assertEquals(2, found.size());
    }

    @Test
    void getAllEmployees_ShouldNotFail() {

        Employee employee = ActiveEmployee.builder()
                .firstName("Ion")
                .lastName("Popescu")
                .yearsExperience(9)
                .salaries(Arrays.asList(Salary.builder().activeFlag(true).currentSalary(2000.00).bonusPercentage(20).build()))
                .build();

        Employee employee1 = RetiredEmployee.builder()
                .firstName("Ion")
                .lastName("Popescu")
                .yearsExperience(10)
                .salaries(Arrays.asList(Salary.builder().activeFlag(true).currentSalary(2000.00).bonusPercentage(20).build()))
                .build();
        Employee saved = repository.saveOrUpdateEmployee(employee).get();
        Employee saved1 = repository.saveOrUpdateEmployee(employee1).get();
        assertEquals(9, saved.getYearsExperience());
        assertEquals(10, saved1.getYearsExperience());

        List<Employee> found = repository.getAllEmployees();

        assertNotNull(found);
        assertEquals(2, found.size());
    }
}