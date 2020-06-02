package com.Training.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Training.Model.PartModel;

public interface PartRepository extends JpaRepository<PartModel, String> {

}
