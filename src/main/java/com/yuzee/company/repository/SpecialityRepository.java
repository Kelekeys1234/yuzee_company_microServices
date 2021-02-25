package com.yuzee.company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuzee.company.model.Speciality;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, String> {
	
	 public List<Speciality > findBySpecialityNameStartsWith(String searchText);

}
