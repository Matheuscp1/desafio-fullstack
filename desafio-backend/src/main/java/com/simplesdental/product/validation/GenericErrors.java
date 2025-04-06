package com.simplesdental.product.validation;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

public class GenericErrors {
	@Getter
	private List<String> errors;

	public GenericErrors(String messageError) {
		this.errors = Collections.singletonList(messageError);
	}
	
	 public GenericErrors(List<String> errors) {
	        this.errors = errors;
	    }
}
