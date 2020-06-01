package com.tutorial.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tutorial.Model.StaffInfoModel;

public interface StaffInfoRepository extends JpaRepository<StaffInfoModel, Long> {
	List<StaffInfoModel> findAll();

	List<StaffInfoModel> findByEmail(String email);

	@Query(value = "SELECT Id,Email,Password,Position,Phone FROM StaffInfo Where Email = ?1 AND Password = ?2", nativeQuery = true)
	List<StaffInfoModel> findCheckStaffInfo(String email, String password);
}
