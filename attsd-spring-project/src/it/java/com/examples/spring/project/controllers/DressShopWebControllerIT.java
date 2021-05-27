package com.examples.spring.project.controllers;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.examples.spring.project.model.DressShop;
import com.examples.spring.project.repositories.DressShopRepository;

@RunWith(SpringRunner.class)

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DressShopWebControllerIT {

	@Autowired
	private DressShopRepository repository;
	
    @Autowired
    private WebApplicationContext wac;

	private MockMvc mvc;
	
	
	@Before
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(wac)
				.build();
		repository.deleteAll();
	}
	
	@After
	public void clearAll() {
		repository.deleteAll();
	}
	
	@Test
	public void test_getIndex() throws Exception {
		mvc.perform(get("/"))
				.andExpect(view().name("index")).andExpect(status().isOk());
	}
	@Test
	public void test_status200() throws Exception {
		mvc.perform(get("/")).andExpect(status().is2xxSuccessful());
	}

	@Test
	public void test_returnHomeView() throws Exception {
		ModelAndViewAssert.assertViewName(mvc.perform(get("/")).andReturn().getModelAndView(), "index");
	}

	@Test
	public void test_emptyDressShopList() throws Exception {
		mvc.perform(get("/")).andExpect(view().name("index"))
				.andExpect(model().attribute("dressShops", new ArrayList<DressShop>()))
				.andExpect(model().attribute("message", "No dress shops"));
		assertEquals(0, repository.count());
	}


	@Test
	public void test_twoDressShops() throws Exception {
		List<DressShop> dressShops = Arrays.asList(new DressShop(null, "Colt", 22), new DressShop(null, "Lacoste", 18));
		repository.saveAll(dressShops);
		mvc.perform(get("/")).andExpect(view().name("index")).andExpect(model().attribute("dressShops", dressShops))
				.andExpect(model().attribute("message", ""));
		assertEquals(2, repository.count());
	}

	@Test
	public void test_singleDressShop() throws Exception {
		DressShop dressShop = new DressShop(null, "Blumarine", 23);
		repository.save(dressShop);
		mvc.perform(get("/edit?id="+dressShop.getId())).andExpect(view().name("edit"))
				.andExpect(model().attribute("dressShop", dressShop)).andExpect(model().attribute("message", "Edit dress shop"));
		assertEquals(1, repository.count());
	}

	@Test
	public void test_singleDressShopNotFound() throws Exception {
		String id = "1";
		mvc.perform(get("/edit?id="+id)).andExpect(view().name("error"))
				.andExpect(model().attribute("dressShop", nullValue()));
		assertEquals(0, repository.count());
	}

	@Test
	public void test_postDressShop() throws Exception {
		DressShop dressShop = new DressShop(Long.valueOf(1), "Birkenstock", 45);
		
		mvc.perform(post("/save")
				.param("id", "" + dressShop.getId())
				.param("name", dressShop.getName())
				.param("targetPrice", "" + dressShop.getTargetPrice()))
		.andExpect(view().name("redirect:/"));
		
		assertEquals(1, repository.count());
	}

	
	@Test
	public void test_newDressShop() throws Exception {
		mvc.perform(get("/new")).andExpect(view().name("edit"))
				.andExpect(model().attribute("dressShop", new DressShop(null, null, 0)))
				.andExpect(model().attribute("message", ""));
	}

	@Test
	public void test_deleteDressShop() throws Exception {
		DressShop dressShop = new DressShop(null, "Birkenstock", 45);
		repository.save(dressShop);
		mvc.perform(get("/delete?id="+dressShop.getId())).andExpect(view().name("redirect:/"));
		assertEquals(0, repository.count());
	}
	
	@Test
	public void test_resetDressShops() throws Exception {
		DressShop dressShop = new DressShop(Long.valueOf(1), "Birkenstock", 45);
		repository.save(dressShop);
		DressShop dressShop2 = new DressShop(Long.valueOf(2), "Diadora", 30);
		repository.save(dressShop2);
		assertEquals(2, repository.count());
		mvc.perform(get("/reset")).andExpect(view().name("redirect:/"));
		assertEquals(0, repository.count());
	}
}
