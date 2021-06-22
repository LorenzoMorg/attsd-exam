package com.examples.spring.project.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.examples.spring.project.model.DressShop;

@DataJpaTest
@ActiveProfiles("h2")
@RunWith(SpringRunner.class)
public class DressShopRepositoryTest {

	@Autowired
	private DressShopRepository repository;

	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void test_findAllDressShopsWithSameTargetPrice() {
		DressShop dshop1 = entityManager.persistFlushFind(new DressShop(null, "Alcott", 20L));
	    entityManager.persistFlushFind(new DressShop(null, "Gucci", 55L));
		DressShop dshop3 = entityManager.persistFlushFind(new DressShop(null, "HM", 20L));
		entityManager.persistFlushFind(new DressShop(null, "Zara", 30L));
		List<DressShop> list = repository.findAllDressShopsWithTheSameTargetPrice(20L);
		assertThat(list).containsExactly(dshop1, dshop3);
	}

	@Test
	public void test_findAllDressShopsOrderedByName() {
		DressShop ds1 = entityManager.persistFlushFind(new DressShop(null, "Zuiki", 15L));
		DressShop ds2 = entityManager.persistFlushFind(new DressShop(null, "Bershka", 20L));
		DressShop ds3 = entityManager.persistFlushFind(new DressShop(null, "Moncler", 40L));
		DressShop ds4 = entityManager.persistFlushFind(new DressShop(null, "Armani", 60L));
		List<DressShop> list = repository.findAllDressShopsOrderedByName();
		assertThat(list).containsExactly(ds4, ds2, ds3, ds1);
	}
	

}
