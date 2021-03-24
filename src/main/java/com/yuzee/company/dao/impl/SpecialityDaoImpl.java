package com.yuzee.company.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuzee.company.dao.SpecialityDao;
import com.yuzee.company.model.Speciality;
import com.yuzee.company.repository.SpecialityRepository;

@Component
public class SpecialityDaoImpl implements SpecialityDao {
	
	@Autowired
	private SpecialityRepository specialityRepository;

	@Override
	public List<Speciality> getSpeciality(String searchText) {
		if (TextUtils.isEmpty(searchText)) {
			return specialityRepository.findAll();
		} else {
			return specialityRepository.findBySpecialityNameStartsWith(searchText);
		}	
	}

	@Override
	public List<Speciality> getAllSpeciality() {
		return specialityRepository.findAll();
	}

	@Override
	public void addAllSpeciality(List<Speciality> listOfSpeciality) {
		specialityRepository.saveAll(listOfSpeciality);
	}

	@Override
	public Optional<Speciality> getSpecialityById(String specialityId) {
		return specialityRepository.findById(specialityId);
	}

	@Override
	public List<Speciality> getSpecialityByIds(Set<String> specialityIds) {
		return specialityRepository.findAllById(specialityIds);
	}
}
