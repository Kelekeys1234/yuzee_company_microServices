package com.yuzee.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.company.model.CompanyPartnerPrimaryDetail;

@Repository
public interface CompanyPartnerPrimaryDetailRepository extends JpaRepository<CompanyPartnerPrimaryDetail, String> {

}
