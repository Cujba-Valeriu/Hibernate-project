package com.cedacri.repositories.impl;

import com.cedacri.entities.Company;
import com.cedacri.repositories.CompanyRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public class CompanyRepositoryImpl implements CompanyRepository {
    private final EntityManager entityManager;

    public CompanyRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Company> saveOrUpdateCompany(Company company) {
        entityManager.getTransaction().begin();
        if (company.getId() == null) {
            entityManager.persist(company);
        } else {
            company = entityManager.merge(company);
        }
        entityManager.getTransaction().commit();

        return company != null ? Optional.of(company) : Optional.empty();
    }

    @Override
    public void deleteCompany(Company company) {
        entityManager.getTransaction().begin(); //uncomment if not using @Transactional

        if (entityManager.contains(company)) {
            entityManager.remove(company);
        } else {
            entityManager.merge(company);
        }
        entityManager.getTransaction().commit(); //uncomment if not using @Transactional
    }

    @Override
    @Transactional
    public Optional<Company> getCompanyById(Long id) {
        Company company = entityManager.find(Company.class, id);
        return company != null ? Optional.of(company) : Optional.empty();
    }

    @Override
    public Optional<Company> getCompanyByName(String name) {
        TypedQuery<Company> query = entityManager.createQuery("select c from Company c where c.name = :name", Company.class);
        query.setParameter("name", name);
        Company company = query.getSingleResult();
        return company != null ? Optional.of(company) : Optional.empty();
    }

    @Override
    @Transactional
    public List<Company> getAllCompanies() {
        Query query = entityManager.createQuery("select c from Company c ");
        return (List<Company>) query.getResultList();
    }


}
