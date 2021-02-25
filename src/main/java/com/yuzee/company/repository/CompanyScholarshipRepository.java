package com.yuzee.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.company.model.CompanyScholarship;

@Repository
public interface CompanyScholarshipRepository extends JpaRepository<CompanyScholarship, String> {
	
	public CompanyScholarship findByCompanyIdAndId(String companyId , String id);
	
	public void deleteByCompanyIdAndId(String companyId , String id);

}
