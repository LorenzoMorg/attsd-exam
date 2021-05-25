package com.examples.spring.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.examples.spring.project.model.DressShop;
import com.examples.spring.project.services.DressShopService;

@Controller
public class DressShopWebController {

	private DressShopService dressShopService;
	public static final String REDIRECT = "redirect:/";
	public static final String MESSAGE = "message";
	public static final String HOMEPAGE = "index";
	public static final String EDIT = "edit";
	public static final String ERROR = "error";

	@Autowired
	public DressShopWebController(DressShopService dressShopService) {
		this.dressShopService = dressShopService;
	}

	@GetMapping("/")
	public String index(Model model) {
		List<DressShop> allDressShops = dressShopService.getAllDressShops();
		model.addAttribute("dressShops", allDressShops);
		model.addAttribute(MESSAGE, allDressShops.isEmpty() ? "No dress shops" : "");
		return HOMEPAGE;
	}

	@GetMapping(value = "/edit")
	public String editDressShop(@RequestParam(name = "id") long id, Model model) {
		DressShop dressShopById = dressShopService.getDressShopById(id);
		if (dressShopById != null) {
			model.addAttribute("dressShop", dressShopById);
			model.addAttribute(MESSAGE, "Edit dress shop");
			return EDIT;
		} else {
			model.addAttribute(MESSAGE, "Error");
			return ERROR;
		}
	}

	@PostMapping("/save")
	public String saveDressShop(DressShop ds) {
		dressShopService.saveIntoDb(ds);
		return REDIRECT;
	}

	@GetMapping("/new")
	public String newDressShop(Model model) {
		DressShop ds = new DressShop();
		model.addAttribute("dressShop", ds);
		model.addAttribute(MESSAGE, "");
		return EDIT;
	}

	@GetMapping(value = "/delete")
	public String deleteDressShop(@RequestParam(name = "id") long id, Model model) {
		DressShop dressShopById = dressShopService.getDressShopById(id);
		if (dressShopById != null) {
			dressShopService.delete(dressShopById);
			return REDIRECT;
		} else {
			model.addAttribute(MESSAGE, "Error");
			return ERROR;
		}
	}

	@GetMapping("/reset")
	public String resetDressShops() {
		dressShopService.deleteAll();
		return REDIRECT;
	}

}

