package com.examples.spring.project.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.examples.spring.project.model.DressShop;
import com.examples.spring.project.repositories.DressShopRepository;

@Service
public class DressShopService {
	
	@Autowired
	private DressShopRepository dressShopRepository;

	public List<DressShop> getAllDressShops() {
		return dressShopRepository.findAll();
	}

	public DressShop getDressShopById(long id) {
		return dressShopRepository.findById(id).orElse(null);
	}

	public DressShop saveIntoDb(DressShop ds) {	
		return dressShopRepository.save(ds);
	}

	public void delete(DressShop ds) {
		dressShopRepository.delete(ds);
	}

	public void deleteAll() {
		dressShopRepository.deleteAll();
	}

}
