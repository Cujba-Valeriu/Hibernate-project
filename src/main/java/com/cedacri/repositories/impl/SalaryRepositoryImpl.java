package com.cedacri.repositories.impl;

import com.cedacri.entities.Salary;
import com.cedacri.repositories.SalaryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Optional;

public class SalaryRepositoryImpl implements SalaryRepository {
    private final EntityManager entityManager;

    public SalaryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Salary> saveOrUpdateSalary(Salary salary) {
        entityManager.getTransaction().begin();

        if (salary.getId() == null) {
            entityManager.persist(salary);
        } else {
            salary = entityManager.merge(salary);
        }
        entityManager.getTransaction().commit();
        return Optional.of(salary);
    }

    @Override
    public void deleteSalary(Salary salary) {
        entityManager.getTransaction().begin();
        if (entityManager.contains(salary)) {
            entityManager.remove(salary);
        } else {
            entityManager.merge(salary);
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public Optional<Salary> getSalaryById(Long id) {
        Salary salary = entityManager.find(Salary.class, id);
        return salary != null ? Optional.of(salary) : Optional.empty();
    }

    @Override
    public List<Salary> getAllSalaries() {
        Query query = entityManager.createQuery("select s from Salary s");
        return (List<Salary>) query.getResultList();
    }

    @Override
    public List<Salary> getSalariesByActiveFlag(boolean isActive) {
        Query query = entityManager.createQuery("SELECT s from Salary s where s.activeFlag =:isActive");
        query.setParameter("isActive", isActive);

        List<Salary> salaries = query.getResultList();
        return salaries;

    }
}
