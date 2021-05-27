package com.examples.spring.project.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.examples.spring.project.model.DressShop;
import com.examples.spring.project.services.DressShopService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = DressShopWebController.class)
public class DressShopWebControllerTest {


	@Autowired
	private MockMvc mvc;

	@MockBean
	private DressShopService dressShopService;

	@Test
	public void test_getIndex() throws Exception {
		mvc.perform(get("/")).andExpect(view().name("index")).andExpect(status().isOk());
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
		verify(dressShopService).getAllDressShops();
	}

	@Test
	public void test_notEmptyDressShopList() throws Exception {
		List<DressShop> dressShops = Arrays.asList(new DressShop(Long.valueOf(1), "Colmar", 30));
		when(dressShopService.getAllDressShops()).thenReturn(dressShops);
		mvc.perform(get("/")).andExpect(view().name("index")).andExpect(model().attribute("dressShops", dressShops))
				.andExpect(model().attribute("message", ""));
		verify(dressShopService).getAllDressShops();
	}

	@Test
	public void test_singleDressShop() throws Exception {
		DressShop ds = new DressShop(Long.valueOf(1), "Blumarine", 23);
		when(dressShopService.getDressShopById(Long.valueOf(1))).thenReturn(ds);
		String id = "1";
		mvc.perform(get("/edit?id="+id)).andExpect(view().name("edit"))
				.andExpect(model().attribute("dressShop", ds)).andExpect(model().attribute("message", "Edit dress shop"));
		verify(dressShopService).getDressShopById(Long.valueOf(1));
	}

	@Test
	public void test_singleDressShopNotFound() throws Exception {
		String id = "1";
		mvc.perform(get("/edit?id="+id)).andExpect(view().name("error"));
		verify(dressShopService).getDressShopById(Long.valueOf(1));
	}

	@Test
	public void test_postDressShop() throws Exception {
		DressShop ds = new DressShop(null, "Birkenstock", 45);
		mvc.perform(post("/save").param("name", ds.getName())
				.param("targetPrice", "" + ds.getTargetPrice())).andExpect(view().name("redirect:/"));

		verify(dressShopService, times(1)).saveIntoDb(ds);
	}

	@Test
	public void test_newDressShop() throws Exception {
		mvc.perform(get("/new")).andExpect(view().name("edit"))
				.andExpect(model().attribute("dressShop", new DressShop(null, null, 0)))
				.andExpect(model().attribute("message", ""));
		verifyNoInteractions(dressShopService);
	}

	@Test
	public void test_deleteDressShopWhenNotExists() throws Exception {
		DressShop ds = new DressShop();
		String id = "1";
		mvc.perform(get("/delete?id="+id)).andExpect(view().name("error"));
		verify(dressShopService, times(1)).getDressShopById(Long.valueOf(1));
		verify(dressShopService, times(0)).delete(ds);
	}
	
	@Test
	public void test_deleteDressShop() throws Exception {
		DressShop ds = new DressShop(Long.valueOf(1), "Diadora", 30);
		when(dressShopService.getDressShopById(Long.valueOf(1))).thenReturn(ds);
		String id = "1";
		mvc.perform(get("/delete?id="+id)).andExpect(view().name("redirect:/"));
		verify(dressShopService, times(1)).getDressShopById(Long.valueOf(1));
		verify(dressShopService, times(1)).delete(ds);
	}

	@Test
	public void test_resetDressShops() throws Exception {
		mvc.perform(get("/reset")).andExpect(view().name("redirect:/"));
		verify(dressShopService).deleteAll();
	}

}
