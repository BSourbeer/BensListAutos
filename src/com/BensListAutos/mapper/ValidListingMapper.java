package com.BensListAutos.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;

import com.BensListAutos.vo.ValidListing;

public interface ValidListingMapper {
	@Results({
		@Result(property = "listingID", column = "listingID"),
		@Result(property = "make", column = "make"),
		@Result(property = "model", column = "model"),
		@Result(property = "year", column = "year")
		
	})
	
	@Insert("INSERT into valid_listing(listingID, make, model, year) "
			+ "VALUES(#{listingID}, #{make}, #{model}, #{year})")
	void insertValidListing (ValidListing validListing);
	
}
