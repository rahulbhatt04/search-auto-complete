package com.autocomplete.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;

public class TrieTest {

	@Test
	public void insertAndDelete() {
		Trie dictionary = new Trie();
		dictionary.insertCity(CityData.builder().name("San_Jose").popularityIndex(1l).build());
		dictionary.insertCity(CityData.builder().name("Fremont").popularityIndex(2l).build());
		dictionary.insertCity(CityData.builder().name("San Juan").popularityIndex(3l).build());
		dictionary.insertCity(CityData.builder().name("San Fran").popularityIndex(5l).build());
		dictionary.insertCity(CityData.builder().name("Santa Clara").popularityIndex(6l).build());
		List<CityData> data = dictionary.getAllSearchResult("Santa");
		assertEquals("santa clara", data.get(0).getName());
		dictionary.delete("santa clara");
		data = dictionary.getAllSearchResult("Santa");
		assertNull(data);
	}

	@Test
	public void sorting() {
		Trie dictionary = new Trie();
		dictionary.insertCity(CityData.builder().name("San_Jose").popularityIndex(100l).build());
		dictionary.insertCity(CityData.builder().name("Fremont").popularityIndex(2l).build());
		dictionary.insertCity(CityData.builder().name("San Juan").popularityIndex(3l).build());
		dictionary.insertCity(CityData.builder().name("San Fran").popularityIndex(5l).build());
		dictionary.insertCity(CityData.builder().name("Santa Clara").popularityIndex(6l).build());
		List<CityData> data = dictionary.getAllSearchResult("San");
		assertEquals(4, data.size());
		assertEquals("san jose", data.get(0).getName());

	}

	@Test
	public void deleteNonExsist() {
		Trie dictionary = new Trie();
		dictionary.insertCity(CityData.builder().name("San_Jose").popularityIndex(100l).build());
		dictionary.delete("santa clara");
	}
}
