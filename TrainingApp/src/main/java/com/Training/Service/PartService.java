package com.Training.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Training.Dao.PartRepository;
import com.Training.Exception.ConflictException;
import com.Training.Model.PartModel;

@Service
public class PartService {

	@Autowired
	PartRepository partRepository;

	// c
	public PartModel createPart(PartModel part) {
		// 檢查DB是否已經存在
		if (partRepository.existsById(part.getId())) {
			throw new ConflictException("ID已存在");
		}

		return partRepository.save(part);
	}

	// r
	public PartModel getPart(String id) {
		return partRepository.findById(id).get();
	}

	// u
	public PartModel updatePart(PartModel part) {
		if (!partRepository.existsById(part.getId())) {
			throw new ConflictException("ID不存在");
		}
		return partRepository.save(part);
	}

	// d
	public void deletePart(String id) {
		partRepository.deleteById(id);
	}

	// find
	public List<PartModel> findAllParts(PartModel part) {

		return partRepository.findAll();
	}

	public List<PartModel> findParts(PartModel part) {
		return partRepository.findByPartNameLike(part.getPartName());
	}

}
