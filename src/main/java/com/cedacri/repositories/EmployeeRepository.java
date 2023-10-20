package com.cedacri.repositories;

import com.cedacri.entities.Company;
import com.cedacri.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {

    Optional<Employee> saveOrUpdateEmployee(Employee employee);

    void deleteEmployee(Employee employee);

    Optional<Employee> getEmployeeById(Long id);

    List<Employee> getEmployeesByExperienceCriteriaQuery(Integer yearsOfExperience);

    Optional<Employee> getEmployeeByFullName(String firstName, String lastName);

    List<Employee> getEmployeesByExperienceJpqlQuery(Integer experience);
    List<Employee> getEmployeesByExperienceNativeQuery(Integer experience);

    List<Employee> getAllEmployees();
}
