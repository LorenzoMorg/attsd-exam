package com.examples.spring.project.model;

public class DressShopDTO {
	
	private Long id;
	private String name;
	private Long targetPrice;

	public DressShopDTO(Long id, String name, Long targetPrice) {
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

	public Long getTargetPrice() {
		return targetPrice;
	}
	
}