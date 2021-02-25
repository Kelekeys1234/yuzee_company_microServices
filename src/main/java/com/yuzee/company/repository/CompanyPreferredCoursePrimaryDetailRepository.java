package com.yuzee.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.company.model.CompanyPreferredCoursePrimaryDetail;

@Repository
public interface CompanyPreferredCoursePrimaryDetailRepository extends JpaRepository<CompanyPreferredCoursePrimaryDetail, String> {

}
