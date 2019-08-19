package com.autocomplete.data;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

/**
 *Node holds following information
 *
 * 1. Charactor in the sequence 
 * 2. All subsequent char reference 
 * 3. From the root on this char what is the word formation looks so far
 * 
 * */
@Getter
@Setter
class Node {
	//key for the char
	private char c;
	//
	private HashMap<Character, Node> children = new HashMap<Character, Node>();
	/*it is memory and speed trade off we can always do the */
	//private Set<String> matchingCities = new HashSet<String>();
	private String wordSoFar;

	private boolean isLeaf;

	public Node() {
	}

	public Node(char c) {
		this.c = c;
	}

	public Node(char c, String city) {
		this.c = c;
		//this.matchingCities.add(city);
	}
}