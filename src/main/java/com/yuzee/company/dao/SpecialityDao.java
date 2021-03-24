package com.yuzee.company.dao;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.yuzee.company.model.Speciality;

public interface SpecialityDao {
	
	public List<Speciality> getSpeciality(String searchText);
	
	public List<Speciality> getAllSpeciality();
	
	public void addAllSpeciality (List<Speciality> listOfSpeciality);
	
	public Optional<Speciality> getSpecialityById (String specialityId);
	
	public List<Speciality> getSpecialityByIds (Set<String> specialityIds);

}
