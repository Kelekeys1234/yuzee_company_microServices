package com.yuzee.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.company.model.CompanyPreferredCourse;

@Repository
public interface CompanyPreferredCourseRepository extends JpaRepository<CompanyPreferredCourse, String> {

}
