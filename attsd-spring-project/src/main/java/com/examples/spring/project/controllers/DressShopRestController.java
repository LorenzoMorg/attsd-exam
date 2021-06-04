package com.examples.spring.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examples.spring.project.model.DressShop;
import com.examples.spring.project.model.DressShopDTO;
import com.examples.spring.project.services.DressShopService;

@RestController
@RequestMapping("/api")
public class DressShopRestController {
	
	private DressShopService dressShopService;

	@Autowired
	public DressShopRestController(DressShopService dressShopService) {
		this.dressShopService = dressShopService;
	}

	@GetMapping("/dressShops")
	public List<DressShop> allDressShops() {
		return dressShopService.getAllDressShops();
	}

	@GetMapping("/dressShops/{id}")
	public DressShop oneDressShop(@PathVariable long id) {
		return dressShopService.getDressShopById(id);
	}

	@PostMapping("/dressShops/new")
	public DressShop newDressShop(@RequestBody DressShopDTO dsDTO) {		
		DressShop ds = new DressShop();
		ds.setId(dsDTO.getId());
		ds.setName(dsDTO.getName());
		ds.setTargetPrice(dsDTO.getTargetPrice());	
		return dressShopService.saveIntoDb(ds);
	}

	@PutMapping("/dressShops/update/{id}")
	public DressShop updateDressShop(@PathVariable long id, @RequestBody DressShopDTO dsDTO) {
		DressShop ds = new DressShop();
		ds.setName(dsDTO.getName());
		ds.setTargetPrice(dsDTO.getTargetPrice());	
		ds.setId(id);
		return dressShopService.saveIntoDb(ds);
	}

	@DeleteMapping("/dressShops/delete/{id}")
	public void deleteDressShop(@PathVariable long id) {
		DressShop dressShopById = dressShopService.getDressShopById(id);
		dressShopService.delete(dressShopById);
}
}
