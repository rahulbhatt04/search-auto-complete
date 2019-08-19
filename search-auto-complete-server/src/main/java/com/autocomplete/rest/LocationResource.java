package com.autocomplete.rest;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.autocomplete.data.CityData;
import com.autocomplete.data.Trie;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/api/v1")
@RestController
public class LocationResource {


	@Autowired
	private Trie trie;
	
	
	@GetMapping("/location/{word}")
	@ApiOperation(value = "Get the data",
    notes = "Pass the word for auto complete")
	public ResponseEntity<List<CityData>> getAutoCompleteList(@PathVariable @NotNull String word) {
		List<CityData> result = trie.getAllSearchResult(word);
		if (result == null || result.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
		}
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/location/{cityName}")
	public void deleteStudent(@PathVariable @Valid @NotNull String cityName) {
		trie.delete(cityName);
	}

	@PostMapping("/location")
	public ResponseEntity<Object> createStudent(@RequestBody @Valid CityData cityData) {
		
		trie.insertCity(cityData);
		return ResponseEntity.accepted().build();


	}
	
}
