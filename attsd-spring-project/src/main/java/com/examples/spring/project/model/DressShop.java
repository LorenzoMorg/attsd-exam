package com.examples.spring.project.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DressShop {
	
	@Id @GeneratedValue
	private Long id;
	private String name;
	private int averagePrice;
	
	public DressShop() {
	}

	public DressShop(Long id, String name, int averagePrice) {
		this.id = id;
		this.name = name;
		this.averagePrice = averagePrice;
	}

	public Long getId() {
			return id;
	}

	public String getName() {
		return name;
	}

	public int getAveragePrice() {
		return averagePrice;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setAveragePrice(int avgPrice) {
		this.averagePrice = avgPrice;		
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "DressShop [id=" + id + ", name=" + name + ", averagePrice=" + averagePrice + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, averagePrice);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DressShop other = (DressShop) obj;
		if (averagePrice != other.averagePrice)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

}
