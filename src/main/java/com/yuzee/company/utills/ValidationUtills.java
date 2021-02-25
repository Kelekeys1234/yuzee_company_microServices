package com.yuzee.company.utills;

import com.yuzee.company.exception.UnauthorizeException;
import com.yuzee.company.model.Company;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationUtills {
	
	private ValidationUtills () {}
	
	public static void validateUserAccess(String userId , Company company) throws UnauthorizeException {
		if (!company.getCreatedBy().equals(userId)) {
			log.error("User id {} don't have access for company id {}",userId, company.getId());
			throw new UnauthorizeException("User don't have acess for company "+ company.getId());
		}
	}
	
	public static boolean isAdmin(String userId , Company company) {
		if (company.getCreatedBy().equals(userId)) {
			return true;
		} else {
			return false;
		}
	}

}
