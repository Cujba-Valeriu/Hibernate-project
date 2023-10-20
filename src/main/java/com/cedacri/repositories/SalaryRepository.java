package com.cedacri.repositories;

import com.cedacri.entities.Company;
import com.cedacri.entities.Salary;

import java.util.List;
import java.util.Optional;

public interface SalaryRepository {
    Optional<Salary> saveOrUpdateSalary(Salary salary);

    void deleteSalary(Salary salary);

    Optional<Salary> getSalaryById(Long id);

    List<Salary> getAllSalaries();

    List<Salary> getSalariesByActiveFlag(boolean isActive);
}
