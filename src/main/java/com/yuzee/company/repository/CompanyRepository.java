package com.yuzee.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.company.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {

}
