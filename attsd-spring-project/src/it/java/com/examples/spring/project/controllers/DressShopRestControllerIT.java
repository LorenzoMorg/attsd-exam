package com.examples.spring.project.controllers;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.examples.spring.project.model.DressShop;
import com.examples.spring.project.repositories.DressShopRepository;

@RunWith(SpringRunner.class)
@ActiveProfiles("mysql")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DressShopRestControllerIT {


	@LocalServerPort
	private int port;
	
	private String url;

	@Autowired
	private DressShopRepository dressShopRepository;

	@Before
	public void setup() {
		url = "http://localhost:" + port;
		dressShopRepository.deleteAll();
	}

	@After
	public void reset() {
		dressShopRepository.deleteAll();
	}

	@Test
	public void test_allDressShopsWithNoDressShop() throws Exception {
		given().when().get(url + "/api/dressShops").then().statusCode(200).assertThat().body(is("[]"));
	}


	@Test
	public void test_allDressShops() throws Exception {
		dressShopRepository.saveAll(Arrays.asList(new DressShop(null, "Colt", 22L), new DressShop(null, "Lacoste", 18L)));
		given().when().get(url + "/api/dressShops").then().statusCode(200).assertThat().body("name[0]",
				equalTo("Colt"), "targetPrice[0]", equalTo(22), "name[1]", equalTo("Lacoste"),
				"targetPrice[1]", equalTo(18));
	}

	@Test
	public void test_findByIdWithExistingDressShop() throws Exception {
		DressShop ds = dressShopRepository.save(new DressShop(null, "Colt", 22L));
		given().when().get(url + "/api/dressShops/" + ds.getId()).then().statusCode(200).assertThat().body("name", equalTo("Colt"), "targetPrice", equalTo(22));
	}

	@Test
	public void test_findByIdWithNonExistingDressShop() throws Exception {
		given().when().get(url + "/api/dressShops/100").then().statusCode(200)
				.contentType("");

	}

	@Test
	public void test_newDressShop() throws Exception {
		given().contentType(MediaType.APPLICATION_JSON_VALUE).body(new DressShop(null, "Alcott", 10L)).when()
				.post(url + "/api/dressShops/new").then().statusCode(200);
		assertThat(dressShopRepository.findAll().toString())
				.matches("\\[DressShop \\[id=([1-9][0-9]*), name=Alcott, targetPrice=10\\]\\]");
	}

	@Test
	public void test_updateDressShop() throws Exception {
		DressShop ds = dressShopRepository.save(new DressShop(null, "Nike", 25L));
		DressShop updated = new DressShop(null, "Adidas", 28L);
		given().contentType(MediaType.APPLICATION_JSON_VALUE).body(updated).when()
				.put(url + "/api/dressShops/update/" + ds.getId()).then().statusCode(200);
		
		assertThat(dressShopRepository.findAll()).hasToString("[DressShop [id=" + ds.getId() + ", name=Adidas, targetPrice=28]]");

	}

	@Test
	public void test_updateDressShopWithFakeId() throws Exception {
		DressShop ds = dressShopRepository.save(new DressShop(null, "Primark", 15L));
		DressShop updated = new DressShop(Long.valueOf(1), "GEOX", 19L);
		given().contentType(MediaType.APPLICATION_JSON_VALUE).body(updated).when()
				.put(url + "/api/dressShops/update/" + ds.getId()).then().statusCode(200);
		
		assertThat(dressShopRepository.findAll()).hasToString("[DressShop [id=" + ds.getId() + ", name=GEOX, targetPrice=19]]");

	}

	@Test
	public void test_deleteDressShop() throws Exception {
		DressShop ds = dressShopRepository.save(new DressShop(Long.valueOf(1), "GAP", 15L));
		DressShop newds = dressShopRepository.save(new DressShop(Long.valueOf(2), "GAPNew", 15L));
		given().when().delete(url + "/api/dressShops/delete/" + ds.getId()).then().statusCode(200);
		assertFalse(dressShopRepository.findById(Long.valueOf(1)).isPresent());
		
		assertThat(dressShopRepository.findAll()).hasToString("[DressShop [id=" + newds.getId() + ", name="+ newds.getName()+ ", targetPrice="+ newds.getTargetPrice()+"]]");
	}
}
