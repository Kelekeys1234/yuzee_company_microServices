package com.yuzee.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.company.model.CompanyCareerAdvice;

@Repository
public interface CompanyCareerAdviceRepository extends JpaRepository<CompanyCareerAdvice, String> {
	
	public CompanyCareerAdvice findByCompanyIdAndId(String companyId, String id);
	
	public void  deleteByCompanyIdAndId(String companyId, String id);
	
}
