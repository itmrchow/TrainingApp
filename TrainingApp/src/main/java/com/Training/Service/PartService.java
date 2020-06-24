package com.Training.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.Training.Converter.PartConverter;
import com.Training.Dao.PartRepository;
import com.Training.Exception.ConflictException;
import com.Training.Model.PartModel;
import com.Training.Request.PartRequest;
import com.Training.Response.PartResponse;

@Service
public class PartService {

	@Autowired
	PartRepository partRepository;

	// c
	public PartModel createPart(PartRequest partRequest) {
		PartModel part = PartConverter.toPartModel(partRequest);

		// 檢查DB是否已經存在
		if (partRepository.existsById(part.getId())) {
			throw new ConflictException("ID已存在");
		}

		return partRepository.save(part);
	}

	// r
	public PartResponse getPart(String id) {
		PartModel partModel = partRepository.findById(id).get();

		return PartConverter.toPartResponse(partModel);
	}

	// u
	public PartResponse updatePart(PartRequest part) {
		PartModel partModel = PartConverter.toPartModel(part);

		if (!partRepository.existsById(partModel.getId())) {
			throw new ConflictException("ID不存在");
		}

		partModel = partRepository.save(partModel);

		return PartConverter.toPartResponse(partModel);
	}

	// d
	public void deletePart(String id) {
		if (!partRepository.existsById(id)) {
			throw new ConflictException("ID不存在");
		}
		partRepository.deleteById(id);
	}

	// find
	public List<PartResponse> findAllParts() {
		List<PartModel> partModels = partRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		return PartConverter.toPartResponseList(partModels);
	}

	public List<PartResponse> findPartsByName(String partName) {
		List<PartModel> partModels = partRepository.findByPartNameLike(partName, Sort.by(Sort.Direction.ASC, "id"));
		return PartConverter.toPartResponseList(partModels);
	}

}
