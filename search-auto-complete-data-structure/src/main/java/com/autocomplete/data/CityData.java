package com.autocomplete.data;

import java.io.Serializable;
import java.util.Comparator;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CityData implements Comparable<CityData>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4733330290784846484L;
	@NotNull
	private Long popularityIndex;
	
	@NotNull
	private String name;

	@Override
	public int compareTo(CityData o) {
		return Comparator.comparingLong(CityData::getPopularityIndex).compare(o,this);
	}

}
