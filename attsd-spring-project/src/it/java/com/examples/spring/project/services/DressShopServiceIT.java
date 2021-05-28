package com.examples.spring.project.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.examples.spring.project.model.DressShop;
import com.examples.spring.project.repositories.DressShopRepository;
import cucumber.api.java.After;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(DressShopService.class)
public class DressShopServiceIT {

	@Autowired
	private DressShopService service;

	@Autowired
	private DressShopRepository repository;
	
	@Before
	public void setup() {
		repository.deleteAll();
	}
	
	@After
	public void clean() {
		repository.deleteAll();
	}

	@Test
	public void test_serviceInsertIntoRepository() {
		DressShop ds = service.saveIntoDb(new DressShop(Long.valueOf(1), "Lacoste", 18));
		assertThat(repository.findById(ds.getId())).isPresent();
	}
	
	@Test
	public void test_serviceUpdateIntoRepository() {
		DressShop ds = repository.save(new DressShop(Long.valueOf(1), "Lacoste", 18));
		DressShop ds2 = service.saveIntoDb(new DressShop(ds.getId(), "Colmar", 30));
		assertThat(repository.findById(ds.getId()).get()).isEqualTo(ds2);
	}
	
	@Test
	public void delete_dressShop() {
		DressShop ds = service.saveIntoDb(new DressShop(Long.valueOf(1), "Lacoste", 18));
		assertEquals(1, repository.count());
		service.delete(ds);
		assertEquals(0, repository.count());
	}
	

}