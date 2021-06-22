package com.examples.spring.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DressShop {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Long targetPrice;
	
	public DressShop() {
	}

	public DressShop(Long id, String name, Long targetPrice) {
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

	public Long getTargetPrice() {
		return targetPrice;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setTargetPrice(Long targetPrice2) {
		this.targetPrice = targetPrice2;		
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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((targetPrice == null) ? 0 : targetPrice.hashCode());
		return result;
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
