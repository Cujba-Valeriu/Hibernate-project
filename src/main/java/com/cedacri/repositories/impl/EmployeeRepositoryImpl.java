package com.cedacri.repositories.impl;

import com.cedacri.entities.Employee;
import com.cedacri.repositories.EmployeeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final EntityManager entityManager;

    public EmployeeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Employee> saveOrUpdateEmployee(Employee employee) {
        entityManager.getTransaction().begin();
        if (employee.getId() == null) {
            entityManager.persist(employee);
        } else {
            employee = entityManager.merge(employee);
        }
        entityManager.getTransaction().commit();
        return Optional.of(employee);
    }

    @Override
//    @Transactional
    public void deleteEmployee(Employee employee) {
        entityManager.getTransaction().begin();

        if (entityManager.contains(employee)) {
            entityManager.remove(employee);
        } else {
            entityManager.merge(employee);
        }

        entityManager.getTransaction().commit();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        Employee employee = entityManager.find(Employee.class, id);
        return employee != null ? Optional.of(employee) : Optional.empty();
    }

    @Override
    public List<Employee> getEmployeesByExperienceCriteriaQuery(Integer yearsOfExperience) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);

        Query query = entityManager.createQuery(criteriaQuery
                .select(employeeRoot)
                .where(criteriaBuilder.greaterThan(employeeRoot.get("yearsExperience"), yearsOfExperience)));

        List<Employee> employees = query.getResultList();

        return employees;
    }

    @Override
    public Optional<Employee> getEmployeeByFullName(String firstName, String lastName) {
        TypedQuery<Employee> jpqlQuery = entityManager.createQuery(
                "SELECT e FROM Employee e WHERE e.firstName = :firstName AND e.lastName = :lastName",
                Employee.class);
        jpqlQuery.setParameter("firstName", firstName);
        jpqlQuery.setParameter("lastName", lastName);

        Employee employee = jpqlQuery.getSingleResult();
        return employee != null ? Optional.of(employee) : Optional.empty();
    }

    @Override
    public List<Employee> getEmployeesByExperienceJpqlQuery(Integer experience) {
        Query jpsqlQuery = entityManager.createQuery(
                "SELECT e FROM Employee as e WHERE e.yearsExperience > " +
                        ":yearsExperience ORDER BY e.lastName");
        jpsqlQuery.setParameter("yearsExperience", experience);
        List<Employee> employees = jpsqlQuery.getResultList();

        return employees;
    }

    @Override
    public List<Employee> getEmployeesByExperienceNativeQuery(Integer experience) {
//        Query nativeQuery = entityManager.createNativeQuery(
//                "SELECT * FROM employees WHERE experience > " +
//                        ":yearsExperience ORDER BY lastName", Employee.class
//        );
//        nativeQuery.setParameter("experience", experience);
//        List<Employee> employees = nativeQuery.getResultList();
//
//        return employees;

        Query query = entityManager.createQuery("select e from Employee e where e.yearsExperience > :experience order by e.lastName");
        query.setParameter("experience", experience);

        return (List<Employee>) query.getResultList();
    }

    @Override
    public List<Employee> getAllEmployees() {
        Query query = entityManager.createQuery("SELECT e FROM Employee e");
        return query.getResultList();
    }
}
