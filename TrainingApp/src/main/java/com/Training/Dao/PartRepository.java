package com.Training.Dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Training.Model.PartModel;

public interface PartRepository extends JpaRepository<PartModel, String> {
//	List<PartModel> findByPartNameLike(String partName);

	@Query("SELECT id , partName FROM PartModel p WHERE p.partName LIKE %:partName%")
	List<PartModel> findByPartNameLike(@Param("partName") String partName, Sort sort);
}
