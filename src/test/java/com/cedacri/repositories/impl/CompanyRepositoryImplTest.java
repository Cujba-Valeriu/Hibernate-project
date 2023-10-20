package com.cedacri.repositories.impl;

import com.cedacri.entities.*;
import com.cedacri.repositories.CompanyRepository;
import com.cedacri.utils.PersistenceManagerTestUtil;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class CompanyRepositoryImplTest {

    static CompanyRepository repository;
    static EntityManager entityManager;

    @BeforeAll
    static void init() {
        entityManager = PersistenceManagerTestUtil.getEntityManger();
        repository = new CompanyRepositoryImpl(entityManager);
    }

    @AfterAll
    static void close() {
        PersistenceManagerTestUtil.closeEntityManagerFactory();
    }

    @BeforeEach
    void cleanDB() {
        List<Company> companies = repository.getAllCompanies();
        for (Company company : companies) {
            repository.deleteCompany(company);
        }
    }

    private List<Employee> getEmployees() {
        Employee employee1 = ActiveEmployee.builder()
                .firstName("Omar")
                .lastName("Ion")
                .yearsExperience(5)
                .build();
        employee1.setProfile(EmployeeProfile.builder().title("Associate").email("i@gmail.com").userName("Gangsta_2000").password("123").employee(employee1).build());
        Employee employee2 = RetiredEmployee.builder()
                .firstName("Gicu")
                .lastName("Gheorghe")
                .yearsExperience(10)
                .build();
        employee2.setProfile(EmployeeProfile.builder().title("Associate").email("v@gmail.com").userName("Pufosenie").password("1234").employee(employee1).build());
        List<Employee> list = new ArrayList<>();
        list.add(employee1);
        list.add(employee2);
        return list;
    }

    @Test
    void saveCompany_ShouldNotFail() {
        Company company = Company.builder()
                .name("Cedacri")
                .city("Chisinau")
                .state("Moldova")
                .country("Moldova")
                .zipcode("2000-MD")
                .employees(getEmployees())
                .build();

        Company saved = repository.saveOrUpdateCompany(company).get();

        Assertions.assertEquals("2000-MD", saved.getZipcode());
        Assertions.assertEquals("Cedacri", saved.getName());
    }

    @Test
    void updateCompany_ShouldNotFail() {
        Company company = Company.builder()
                .name("Cedacri")
                .city("Chisinau")
                .state("Moldova")
                .country("Moldova")
                .zipcode("2000-MD")
                .employees(getEmployees())
                .build();

        Company saved = repository.saveOrUpdateCompany(company).get();
        Assertions.assertEquals("2000-MD", saved.getZipcode());

        saved.setZipcode("2000-NEW");
        Company updated = entityManager.merge(saved);
        Assertions.assertEquals("2000-NEW", updated.getZipcode());


    }

    @Test
    void deleteCompany_ShouldNotFail() {
        Company company = Company.builder()
                .name("Cedacri")
                .city("Chisinau")
                .state("Moldova")
                .country("Moldova")
                .zipcode("2000-MD")
                .employees(getEmployees())
                .build();

        Company saved = repository.saveOrUpdateCompany(company).get();
        Assertions.assertNotNull(repository.getAllCompanies());

        repository.deleteCompany(saved);
        Assertions.assertEquals(0, repository.getAllCompanies().size());
    }

    @Test
    void getCompanyById_ShouldNotFail() {
        Company company = Company.builder()
                .name("Cedacri")
                .city("Chisinau")
                .state("Moldova")
                .country("Moldova")
                .zipcode("2000-MD")
                .employees(getEmployees())
                .build();

        Company saved = repository.saveOrUpdateCompany(company).get();
        Assertions.assertNotNull(repository.getAllCompanies());

        Company foundById = repository.getCompanyById(saved.getId()).get();
        Assertions.assertNotNull(foundById);
        Assertions.assertEquals("Cedacri", foundById.getName());

    }

    @Test
    void getCompanyByName_ShouldNotFail() {
        Company company = Company.builder()
                .name("Cedacri")
                .city("Chisinau")
                .state("Moldova")
                .country("Moldova")
                .zipcode("2000-MD")
                .employees(getEmployees())
                .build();

        Company saved = repository.saveOrUpdateCompany(company).get();
        Assertions.assertNotNull(repository.getAllCompanies());

        Company foundByName = repository.getCompanyByName("Cedacri").get();
        Assertions.assertNotNull(foundByName);
        Assertions.assertNotEquals(Optional.empty(), foundByName);
        Assertions.assertEquals("2000-MD", foundByName.getZipcode());

    }

    @Test
    void getAllCompanies_ShouldNotFail() {
        Company company1 = Company.builder()
                .name("Cedacri")
                .city("Chisinau")
                .state("Moldova")
                .country("Moldova")
                .zipcode("2000-MD")
                .employees(getEmployees())
                .build();
        Company company2 = Company.builder()
                .name("Endava")
                .city("Chisinau")
                .state("Moldova")
                .country("Moldova")
                .zipcode("2001-MD")
                .employees(getEmployees())
                .build();
        Company company3 = Company.builder()
                .name("Orange")
                .city("Chisinau")
                .state("Moldova")
                .country("Moldova")
                .zipcode("2010-MD")
                .employees(getEmployees())
                .build();

        repository.saveOrUpdateCompany(company1);
        repository.saveOrUpdateCompany(company2);
        repository.saveOrUpdateCompany(company3);

        List<Company> companiesFromDB = repository.getAllCompanies();
        Assertions.assertNotNull(companiesFromDB);
        Assertions.assertEquals(3, companiesFromDB.size());
    }
}