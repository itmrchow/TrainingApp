package com.Training.Controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Training.Model.PartModel;
import com.Training.Request.PartRequest;
import com.Training.Response.PartResponse;
import com.Training.Service.PartService;

@RestController
@RequestMapping(value = "/part", produces = MediaType.APPLICATION_JSON_VALUE)
//produces = 回傳型態
public class PartController {

	@Autowired
	PartService partService;

	// R
	@GetMapping(value = "/{id}")
	// ResponseEntity = 回應實體
	public ResponseEntity<PartResponse> getPart(@PathVariable("id") String id) {
		PartResponse partResponse = partService.getPart(id);
		return ResponseEntity.ok().body(partResponse);
	}

	// FIND
	@GetMapping
	public ResponseEntity<List<PartResponse>> findParts(@RequestParam(value = "partName", required = false) String partName) {
		List<PartResponse> parts = new ArrayList<PartResponse>();
		if (partName == null) {
			parts = partService.findAllParts();
		} else {
			parts = partService.findPartsByName(partName);
		}
		return ResponseEntity.ok().body(parts);
	}

	// C
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PartModel> createPart(@Valid @RequestBody PartRequest partRequest) {
		// 欄位驗證
		PartModel partModel = partService.createPart(partRequest);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(partModel.getId()).toUri();
		return ResponseEntity.created(location).body(partModel);
	}

	// U
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PartResponse> updatePart(@Valid @RequestBody PartRequest partRequest) {
		// 欄位驗證
		PartResponse partResponse = partService.updatePart(partRequest);
		return ResponseEntity.ok().body(partResponse);
	}

	// D
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletePart(@PathVariable("id") String id) {
		partService.deletePart(id);

		boolean isRemoved = true;

		if (isRemoved) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
