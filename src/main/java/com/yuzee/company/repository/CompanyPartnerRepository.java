package com.yuzee.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.company.model.CompanyPartner;

@Repository
public interface CompanyPartnerRepository extends JpaRepository<CompanyPartner, String> {

}
