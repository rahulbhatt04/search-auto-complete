package com.autocomplete.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class Trie {
	private Node root;

	private static String SPACE_SPECIAL_CHAR = "_";

	private static String SPACE_CHAR = " ";
		
	/**
	 * maintaining a Map of all cities or meta data in the map 
	 * Ideally it can be a lRU cached based by database
	 * */
	private Map<String, Long> allCities;

	public Trie() {
		root = new Node();
		allCities=new HashMap<String,Long>();
	}

	public void insertCity(CityData cityData) {
		if (cityData.getName() == null) {
			return;
		} else {
			cityData.setName(cityData.getName().toLowerCase());
			cityData.setName(cityData.getName().replace(SPACE_SPECIAL_CHAR, SPACE_CHAR));
		}
		this.insert(cityData.getName());
		this.allCities.put(cityData.getName(), cityData.getPopularityIndex());
	}

	// Inserts a word into the trie.
	private void insert(String word) {
		HashMap<Character, Node> children = root.getChildren();
		StringBuffer sf = new StringBuffer();
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			sf.append(c);
			Node t;
			if (children.containsKey(c)) {
				t = children.get(c);
				// t.getMatchingCities().add(word);
			} else {
				t = new Node(c);
				t.setWordSoFar(sf.toString());
				children.put(c, t);
			}

			children = t.getChildren();

			// set leaf node
			if (i == word.length() - 1)
				t.setLeaf(true);
		}
	}
	@Cacheable("")
	public List<CityData> getAllSearchResult(String str) {
		str=str.toLowerCase();
		Map<Character, Node> children = root.getChildren();
		Node t = null;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (children.containsKey(c)) {
				t = children.get(c);
				children = t.getChildren();
			} else {
				return null;
			}
		}
		List<CityData> list = getAllTheWords(t);
		Collections.sort(list);
		return list;
	}

	private List<CityData> getAllTheWords(Node node) {
		if (node.isLeaf()) {
			return Arrays.asList(CityData.builder().name(node.getWordSoFar())
					.popularityIndex(this.allCities.get(node.getWordSoFar())).build());
		}
		List<CityData> list = new LinkedList<>();
		for (Node tn : node.getChildren().values())
			list.addAll(getAllTheWords(tn));
		return list;
	}
	
	public void delete(String city)
	{
		this.delete(root, city.toLowerCase(), 0);
		this.allCities.remove(city.toLowerCase());
	}
	private boolean delete(Node current, String word, int index) {
	    if (index == word.length()) {
	        if (!current.isLeaf()) {
	            return false;
	        }
	        current.setLeaf(false);
	        return current.getChildren().isEmpty();
	    }
	    char ch = word.charAt(index);
	    Node node = current.getChildren().get(ch);
	    if (node == null) {
	        return false;
	    }
	    boolean shouldDeleteCurrentNode = delete(node, word, index + 1) && !node.isLeaf();
	 
	    if (shouldDeleteCurrentNode) {
	        current.getChildren().remove(ch);
	        return current.getChildren().isEmpty();
	    }
	    return false;
	}

}