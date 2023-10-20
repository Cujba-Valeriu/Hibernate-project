package com.cedacri.repositories;

import com.cedacri.entities.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository {
    Optional<Company> saveOrUpdateCompany(Company company);

    void deleteCompany(Company company);

    Optional<Company> getCompanyById(Long id);

    Optional<Company> getCompanyByName(String name);

    List<Company> getAllCompanies();

}
