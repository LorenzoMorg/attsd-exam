package com.examples.spring.project.services;

import com.examples.spring.project.model.DressShop;
import com.examples.spring.project.repositories.DressShopRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.ArgumentCaptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DressShopService.class)
public class DressShopServiceWithMockBeanTest {
	
	@MockBean
	private DressShopRepository dressShopRepository;

	@Autowired
	private DressShopService dressShopService;

	@Test
	public void test_getAllDressShopsWithNoDressShops() {
		assertEquals(0, dressShopService.getAllDressShops().size());
		verify(dressShopRepository, times(1)).findAll();
	}

	@Test
	public void test_getAllDressShopsWithOneDressShop() {
		DressShop ds= new DressShop(1L, "firstDS", 18L);
		when(dressShopRepository.findAll())
				.thenReturn(Arrays.asList(ds));
		assertThat(dressShopService.getAllDressShops()).containsExactly(ds);
		verify(dressShopRepository, times(1)).findAll();
	}

	@Test
	public void test_getAllDressShopsWithSomeDressShops() {
		DressShop dressShop1 = new DressShop(1L, "firstDS", 30L);
		DressShop dressShop2 = new DressShop(2L, "secondDS", 45L);
		when(dressShopRepository.findAll()).thenReturn(Arrays.asList(dressShop1, dressShop2));
		assertThat(dressShopService.getAllDressShops()).containsExactly(dressShop1, dressShop2);
		verify(dressShopRepository, times(1)).findAll();
	}

	@Test
	public void test_getDressShopByIdFound() {
		DressShop dressShop = new DressShop(1L, "firstDS", 30L);
		Optional<DressShop> expected = Optional.of(dressShop);
		when(dressShopRepository.findById(1L)).thenReturn(expected);
		assertThat(dressShopService.getDressShopById(1L)).isSameAs(dressShop);
		verify(dressShopRepository, times(1)).findById(1L);
	}

	@Test
	public void test_getDressShopByIdNotFound() {
		DressShop actual = dressShopService.getDressShopById(1L);
		assertNull(actual);
		verify(dressShopRepository, times(1)).findById(1L);
	}

	@Test
	public void test_deleteDressShop() {
		DressShop ds = new DressShop(1L, "Lacoste", 18L);
		dressShopService.delete(ds);
		verify(dressShopRepository, times(1)).delete(ds);
	}

	@Test
	public void test_deleteAllDressShops() {
		dressShopService.deleteAll();
		verify(dressShopRepository, times(1)).deleteAll();
	}
	
	@Test
	public void test_saveIntoDb() {
		ArgumentCaptor<DressShop> captor = ArgumentCaptor.forClass(DressShop.class);
		DressShop ds = new DressShop(1L, "Lacoste", 18L);
		dressShopService.saveIntoDb(ds);
		verify(dressShopRepository, times(1)).save(captor.capture());
		DressShop passedToRepository = captor.getValue();
		assertEquals(Long.valueOf(1), passedToRepository.getId());
		assertEquals("Lacoste", passedToRepository.getName());
		assertEquals(Long.valueOf(18), passedToRepository.getTargetPrice());
	}
}
