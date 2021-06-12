package com.examples.spring.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.examples.spring.project.model.DressShop;

public interface DressShopRepository extends JpaRepository<DressShop, Long> {


	@Query("Select ds from DressShop ds where ds.targetPrice = :n")
	List<DressShop> findAllDressShopsWithTheSameTargetPrice(@Param("n") Long n);
	

	@Query("Select ds from DressShop ds order by name")
	List<DressShop> findAllDressShopsOrderedByName();

	
}
