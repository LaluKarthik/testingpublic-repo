package com.uvaan.sampleprojectapi.validations;

import java.util.Date;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.uvaan.sampleprojectapi.constants.Constants;
import com.uvaan.sampleprojectapi.param.UserParam;
import com.uvaan.sampleprojectapi.utils.CustomUtils;

public class UserValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return UserValidation.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors error) {
		UserParam params = (UserParam) target;

		addDefalts(params);

		if (CustomUtils.isEmpty(params.getEmail())) {
			error.rejectValue("email", Constants.BAD_REQUEST_ERROR_CD, "is an empty or null");
		}
		if (CustomUtils.isEmpty(params.getPassword())) {
			error.rejectValue("password", Constants.BAD_REQUEST_ERROR_CD, "is an empty or null");
		}

	}

	public void validateupdate(Object target, Errors error) {
		UserParam params = (UserParam) target;

		addDefalts(params);

		if (null == params.getId() || params.getId() <= 0) {
			error.rejectValue("id", Constants.BAD_REQUEST_ERROR_CD, "please provide valid entity id");
		}

	}

	private void addDefalts(UserParam params) {
		if (null == params.getId() || params.getId() <= 0) {
			params.setCreatedBy(new Long(100));
			params.setCreatedDate(new Date());
			

		} else {
		
			params.setUpdatedBy(new Long(100));
			params.setUpdatedDate(new Date());
		}

	}
}
