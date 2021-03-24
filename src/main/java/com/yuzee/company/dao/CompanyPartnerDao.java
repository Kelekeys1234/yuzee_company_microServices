package com.yuzee.company.dao;

import java.util.List;

import com.yuzee.company.exception.BadRequestException;
import com.yuzee.company.model.CompanyPartner;

public interface CompanyPartnerDao {

	public void addUpdateAllCompanyParter (List<CompanyPartner> listOfCompanyPartner) throws BadRequestException;
	
}
