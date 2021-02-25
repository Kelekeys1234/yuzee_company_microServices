package com.yuzee.company.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.company.enumeration.WorkWithUs;
import com.yuzee.company.model.CompanyWorkWithUs;

@Repository
public interface CompanyWorkWithUsRepository extends JpaRepository<CompanyWorkWithUs, String> {
	
	public Optional<CompanyWorkWithUs> findByCompanyIdAndWorkWithUs (String companyId, WorkWithUs workWithUs);

	public void deleteByCompanyIdAndId(String companyId , String companyWorkWithUsId);
}
