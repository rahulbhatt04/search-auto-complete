package com.autocomplete.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autocomplete.data.CityData;
import com.autocomplete.data.Trie;

@Service
public class CitySeedDataPopulator {
	
	@Autowired
	private Trie trie;
 
	@PostConstruct
	public void init() throws IOException {
		InputStream resource = Thread.currentThread().getContextClassLoader().getSystemResourceAsStream("location-cnt.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(resource));
        String line;
		while( (line = reader.readLine()) != null){
        	StringTokenizer stringTokenizer =new StringTokenizer(line," ");
        	Long popularity = Long.parseLong(stringTokenizer.nextToken());
        	String city = stringTokenizer.nextToken();
        	this.trie.insertCity(CityData.builder().name(city).popularityIndex(popularity).build());
        }
		
	}
}
