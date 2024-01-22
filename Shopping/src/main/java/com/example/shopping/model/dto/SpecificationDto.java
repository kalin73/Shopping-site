package com.example.shopping.model.dto;

import com.example.shopping.model.entity.SpecificationsEntity;

public class SpecificationDto {

	private String name;

	private String value;

	public String getName() {
		return name;
	}

	public SpecificationDto setName(String name) {
		this.name = name;
		return this;
	}

	public String getValue() {
		return value;
	}

	public SpecificationDto setValue(String value) {
		this.value = value;
		return this;
	}
	
	public static SpecificationDto mapToSpecDto(SpecificationsEntity specificationsEntity) {
		SpecificationDto spec = new SpecificationDto();
		
		spec.setName(specificationsEntity.getName())
			.setValue(specificationsEntity.getValue());
		
		return spec;
	}

}
