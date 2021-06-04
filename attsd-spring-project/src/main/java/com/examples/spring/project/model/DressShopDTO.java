package com.examples.spring.project.model;

public class DressShopDTO {
	
	private Long id;
	private String name;
	private int targetPrice;

	public DressShopDTO(Long id, String name, int targetPrice) {
		this.id = id;
		this.name = name;
		this.targetPrice = targetPrice;
	}
	
	public DressShopDTO() {	
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getTargetPrice() {
		return targetPrice;
	}
	
}