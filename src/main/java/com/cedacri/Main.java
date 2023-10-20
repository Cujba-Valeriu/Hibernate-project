package com.cedacri;

import com.cedacri.entities.*;
import com.cedacri.repositories.EmployeeRepository;
import com.cedacri.repositories.impl.EmployeeRepositoryImpl;
import com.cedacri.utils.PersistenceManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

import java.util.*;

public class Main {

    static EntityManager entityManager = PersistenceManager.getEntityManger();

    public static void main(String[] args) {

        EmployeeRepositoryImpl employeeRepository = new EmployeeRepositoryImpl(entityManager);

        ActiveEmployee employee = new ActiveEmployee();
        employee.setFirstName("Mary");
        employee.setLastName("Johnson");
        employee.setYearsExperience(20);

        ActiveEmployee employee2 = new ActiveEmployee();
        employee2.setFirstName("John");
        employee2.setLastName("Doe");
        employee2.setYearsExperience(5);

        Employee employee1 = new Employee();
        employee1.setFirstName("Ion");
        employee1.setLastName("Dogue");
        employee1.setYearsExperience(10);

        //set employment history
        employee.setCompanies(generateCompanies());
        employee2.setCompanies(generateCompanies());
        employee1.setCompanies(generateCompanies());
        //create an EmployeeProfile and associate it to an Employee
        employee.setProfile(new EmployeeProfile("userName", "password!", "email@email.com", "Software Engineer", employee));
        employee2.setProfile(new EmployeeProfile("jDoe", "password234", "johndoe@email.com", "Project Manager", employee2));
        employee1.setProfile(new EmployeeProfile("Ion", "passwordIon!", "ion@email.com", "Java Dev", employee1));
        //set salaries
        employee.setSalaries(generateSalaries());
        employee2.setSalaries(generateSalaries());
        employee1.setSalaries(generateSalaries());

        //save Employee
        employeeRepository.saveOrUpdateEmployee(employee);
        employeeRepository.saveOrUpdateEmployee(employee2);
        employeeRepository.saveOrUpdateEmployee(employee1);

        employeeRepository.getEmployeesByExperienceCriteriaQuery(10).stream().forEach(e -> System.out.println(e.getFirstName()));
        employeeRepository.getEmployeeByFullName("John", "Doe").stream().forEach(e -> System.out.println(e.getYearsExperience()));
        employeeRepository.getEmployeesByExperienceNativeQuery(4).stream().forEach(e -> System.out.println(e.getFirstName()));

        //delete activeEmployee
        employeeRepository.deleteEmployee(employee);

        List<Employee> employeeList = employeeRepository.getAllEmployees();
        employeeList.stream().forEach(e -> System.out.println(e.getYearsExperience()));

        entityManager.close();
        PersistenceManager.closeEntityManagerFactory();
    }

    private static List<Company> generateCompanies() {
        Company company1 = new Company("Google", "USA");
        Company company2 = new Company("Amazon", "USA");

        List<Company> companies = new ArrayList<>();
        companies.add(company1);
        companies.add(company2);

        return companies;
    }

    private static List<Salary> generateSalaries() {
        //create the Salaries and associate to Employee
        Salary currentSalary = new Salary(34000.00, true);
        Salary historicalSalary1 = new Salary(10000.00, false);
        Salary historicalSalary2 = new Salary(5000.00, false);

        List<Salary> salaries = new ArrayList<>();
        salaries.add(currentSalary);
        salaries.add(historicalSalary1);
        salaries.add(historicalSalary2);

        return salaries;
    }
}