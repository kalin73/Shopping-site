package com.example.shopping.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.shopping.model.entity.ProductEntity;
import com.example.shopping.model.entity.SpecificationsEntity;
import com.example.shopping.repository.SpecificationsRepository;

@Service
public class SpecificationsService {
	private final SpecificationsRepository specificationsRepository;

	public SpecificationsService(SpecificationsRepository specificationsRepository) {
		this.specificationsRepository = specificationsRepository;
	}

	public void addSpec(SpecificationsEntity spec) {
		this.specificationsRepository.save(spec);
	}

	public void addAll(List<SpecificationsEntity> specs) {
		this.specificationsRepository.saveAll(specs);
	}

	public List<SpecificationsEntity> getAllSpecsByProduct(ProductEntity product) {
		return this.specificationsRepository.findAllByProduct(product);
	}
}
