package com.examples.spring.project.controllers;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;
import static org.hamcrest.CoreMatchers.equalTo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;


import com.examples.spring.project.model.DressShop;
import com.examples.spring.project.model.DressShopDTO;
import com.examples.spring.project.services.DressShopService;


public class DressShopRestControllerRestAssuredTest {
	

	private DressShopService dressShopService;

	@Before
	public void setup() {
		dressShopService = mock(DressShopService.class);
		standaloneSetup(new DressShopRestController(dressShopService));
	}

	@Test
	public void test_allDressShops() throws Exception {
		when(dressShopService.getAllDressShops())
				.thenReturn(Arrays.asList(new DressShop(1L, "GAP", 15L),
						new DressShop(2L, "Colt", 22L)));
		given().when().get("/api/dressShops").then().statusCode(200).assertThat().body("id[0]", equalTo(1), "name[0]",
				equalTo("GAP"), "targetPrice[0]", equalTo(15), "id[1]", equalTo(2), "name[1]", equalTo("Colt"),
				"targetPrice[1]", equalTo(22));
		verify(dressShopService, times(1)).getAllDressShops();
	}

	@Test
	public void test_findByIdWithExistingDressShop() throws Exception {
		when(dressShopService.getDressShopById(Long.valueOf(1)))
				.thenReturn(new DressShop(1L, "Diesel", 30L));
		given().when().get("/api/dressShops/1").then().statusCode(200).assertThat().body("id", equalTo(1), "name",
				equalTo("Diesel"), "targetPrice", equalTo(30));
		verify(dressShopService, times(1)).getDressShopById(Long.valueOf(1));
	}

	@Test
	public void test_findByIdWithNonExistingDressShop() throws Exception {
		given().when().get("/api/dressShops/1").then().statusCode(200).contentType(emptyOrNullString());
		verify(dressShopService, times(1)).getDressShopById(Long.valueOf(1));
	}

	@Test
	public void test_newDressShop() throws Exception {
		
		DressShopDTO dsDTO = new DressShopDTO(null, "Adidas", 20L);
		DressShop ds = new DressShop();
		ds.setId(dsDTO.getId());
		ds.setName(dsDTO.getName());
		ds.setTargetPrice(dsDTO.getTargetPrice());

		given().contentType(MediaType.APPLICATION_JSON_VALUE).body(dsDTO).when().post("/api/dressShops/new").then()
				.statusCode(200);
		ArgumentCaptor<DressShop> captor = ArgumentCaptor.forClass(DressShop.class);
		verify(dressShopService, times(1)).saveIntoDb(captor.capture());
		assertEquals(captor.getValue().getId(), ds.getId());
		assertEquals(captor.getValue().getName(), ds.getName());
		assertEquals(captor.getValue().getTargetPrice(), ds.getTargetPrice());
	}
	

	@Test
	public void test_updateDressShop() throws Exception {  
		when(dressShopService.getDressShopById(Long.valueOf(1)))
				.thenReturn(new DressShop(1L, "Nike", 20L));
		DressShop updated = new DressShop(1L, "Puma", 21L);
		given().contentType(MediaType.APPLICATION_JSON_VALUE).body(updated).when().put("/api/dressShops/update/1")
				.then().statusCode(200);
		verify(dressShopService, times(1)).saveIntoDb(updated);
	}

	@Test
	public void test_updateDressShopWithFakeId() throws Exception {
		DressShop updated = new DressShop(1L, "Gutteridge", 35L);
		given().contentType(MediaType.APPLICATION_JSON_VALUE).body(updated).when().put("/api/dressShops/update/1")
				.then().statusCode(200);
		verify(dressShopService, times(1)).saveIntoDb(updated);

	}

	@Test
	public void test_deleteDressShop() throws Exception {
		DressShop ds = new DressShop(1L, "Diesel", 30L);
		when(dressShopService.getDressShopById(Long.valueOf(1)))
		.thenReturn(ds);
		given().when().delete("/api/dressShops/delete/1").then().statusCode(200);
		verify(dressShopService, times(1)).getDressShopById(Long.valueOf(1));
		verify(dressShopService, times(1)).delete(ds);

	}
	
	@Test
	public void test_deleteDressShopNotExists() throws Exception {
		DressShop ds = new DressShop();
		given().when().delete("/api/dressShops/delete/1").then().statusCode(200);
		verify(dressShopService, times(1)).getDressShopById(Long.valueOf(1));
		verify(dressShopService, times(0)).delete(ds);
	}
}
