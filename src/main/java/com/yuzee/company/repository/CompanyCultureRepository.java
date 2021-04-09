package com.yuzee.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.company.model.CompanyCulture;

@Repository
public interface CompanyCultureRepository extends JpaRepository<CompanyCulture, String> {
	
	public CompanyCulture findByCompanyId (String companyId);

}
