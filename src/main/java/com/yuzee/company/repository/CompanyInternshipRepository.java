package com.yuzee.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.company.model.CompanyInternship;

@Repository
public interface CompanyInternshipRepository extends JpaRepository<CompanyInternship, String> {
	
	public CompanyInternship findByCompanyIdAndId(String companyId , String id);
	
	public void deleteByCompanyIdAndId(String companyId , String id);
}
