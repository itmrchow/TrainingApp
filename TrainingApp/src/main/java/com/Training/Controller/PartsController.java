package com.Training.Controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Training.Model.PartModel;
import com.Training.Service.PartService;

@RestController
@RequestMapping(value = "/part", produces = MediaType.APPLICATION_JSON_VALUE)
//produces = 回傳型態
public class PartsController {

	@Autowired
	PartService partService;

	// R
	@GetMapping(value = "/{id}")
	// ResponseEntity = 回應實體
	public ResponseEntity<PartModel> getPart(@PathVariable("id") String id) {
		PartModel part = partService.getPart(id);
		return ResponseEntity.ok().body(part);
	}

	// FIND
	@GetMapping
	public ResponseEntity<List<PartModel>> findParts(@RequestParam(value = "partName", required = false) String partName) {
		List<PartModel> parts = new ArrayList<PartModel>();
		if (partName == null) {
			parts = partService.findAllParts();
		} else {
			parts = partService.findPartsByName(partName);
		}
		return ResponseEntity.ok().body(parts);
	}

	// C
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PartModel> createPart(@RequestBody PartModel part) {
		// 欄位驗證
		partService.createPart(part);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(part.getId()).toUri();
		return ResponseEntity.created(location).body(part);
	}

	// U
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PartModel> updatePart(@RequestBody PartModel part) {
		// 欄位驗證
		part = partService.updatePart(part);
		return ResponseEntity.ok().body(part);
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
