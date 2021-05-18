package com.examples.spring.project.model;

public class DressShop {
	
	private long id;
	private String name;
	private int averagePrice;

	public DressShop(long id, String name, int averagePrice) {
		this.id = id;
		this.name = name;
		this.averagePrice = averagePrice;
	}
	
	public DressShop() {	
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAveragePrice() {
		return averagePrice;
	}
	
	@Override
	public String toString() {
		return "DressShop [id=" + id + ", name=" + name + ", averagePrice=" + averagePrice + "]";
	}

}
