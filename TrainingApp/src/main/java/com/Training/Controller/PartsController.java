package com.Training.Controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Training.Dao.PartRepository;
import com.Training.Model.PartModel;

@RestController
@RequestMapping(value = "/part", produces = MediaType.APPLICATION_JSON_VALUE)
//produces = 回傳型態
public class PartsController {

	@Autowired
	PartRepository partRepository;

	// R
	@GetMapping(value = "/{id}")
	// ResponseEntity = 回應實體
	public ResponseEntity<PartModel> getPart(@PathVariable("id") String id) {
		PartModel part = partRepository.findById(id).get();
//		part.setId("leg");
//		part.setPartsName("腿");
//		part.setCreateDate(new Date());
//		part.setUpdateDate(new Date());

		return ResponseEntity.ok().body(part);
	}
	
	@GetMapping
	public ResponseEntity<List<PartModel>> findParts(){
		return null;
	}

	// C
	// @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PartModel> createPart(@RequestBody PartModel part) {
		// 欄位驗證
		partRepository.save(part);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(part.getId()).toUri();
		return ResponseEntity.created(location).body(part);
	}

	// U
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PartModel> updatePart(@RequestBody PartModel part) {
		// 欄位驗證

		partRepository.save(part);
		return ResponseEntity.ok().body(part);
	}

	// D
	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public PartModel deletePart(@PathVariable("id") String id) {
		partRepository.deleteById(id);
		return null;
	}

	@GetMapping(value = "test")
	public String test(Model model) {
		return "test";
	}

}
