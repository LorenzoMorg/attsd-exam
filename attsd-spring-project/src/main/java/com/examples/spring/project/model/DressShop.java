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
	private int targetPrice;
	
	public DressShop() {
	}

	public DressShop(Long id, String name, int targetPrice) {
		this.id = id;
		this.name = name;
		this.targetPrice = targetPrice;
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
	
	public void setName(String name) {
		this.name = name;
	}

	public void setTargetPrice(int avgPrice) {
		this.targetPrice = avgPrice;		
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "DressShop [id=" + id + ", name=" + name + ", targetPrice=" + targetPrice + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, targetPrice);
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
		if (targetPrice != other.targetPrice)
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
