package com.BensListAutos.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.BensListAutos.vo.Listing;
import com.BensListAutos.vo.ValidListingSearchRequest;
import com.BensListAutos.vo.ValidListingSearchResult;

public interface ListingMapper {
	@Results({
		@Result(property = "listingID", column = "listingID"),
		@Result(property = "title", column = "title"),
		@Result(property = "url", column = "url"),
		@Result(property = "location", column = "location"),
		@Result(property = "yearMakeModel", column = "yearMakeModel"),
		@Result(property = "odometer", column = "odometer"),
		@Result(property = "condition", column = "vehicleCondition"),
		@Result(property = "cylinders", column = "cylinders"),
		@Result(property = "drive", column = "drive"),
		@Result(property = "fuel", column = "fuel"),
		@Result(property = "color", column = "color"),
		@Result(property = "titleStatus", column = "titleStatus"),
		@Result(property = "size", column = "size"),
		@Result(property = "transmission", column = "transmission"),
		@Result(property = "type", column = "vehicleType"),
		@Result(property = "description", column = "description"),
		@Result(property = "price", column = "price"),
		@Result(property = "postedDate", column = "postedDate"),
		@Result(property = "validateStatus", column = "validateStatus"),
		@Result(property = "state", column = "state"),
		@Result(property = "year", column = "year"),
		@Result(property = "make", column = "make"),
		@Result(property = "model", column = "model")
	})
	
	@Insert("INSERT into Listing(title, url, location, yearMakeModel, odometer, vehicleCondition, cylinders, drive, fuel, color, titleStatus, size, transmission, vehicleType, description, price, postedDate, validateStatus) "
			+ "VALUES(#{title}, #{url}, #{location}, #{yearMakeModel}, #{odometer}, #{condition}, #{cylinders}, #{drive}, #{fuel}, #{color}, #{titleStatus}, #{size}, #{transmission}, #{type}, #{description}, #{price}, #{postedDate}, #{validateStatus})")
	void insertListing (Listing listing);
	
	@Select("SELECT * from Listing WHERE validateStatus = #{selectValidateStatus} LIMIT #{limit} OFFSET #{offset}")
	List<Listing> selectPagedListings(@Param("limit") int limit, @Param("offset") int offset, @Param("selectValidateStatus") String selectValidateStatus);
	
	@Update("UPDATE Listing SET validateStatus = #{validateStatus} WHERE listingID = #{listingID}")
	void updateValidateStatus(Listing listing);
	
	@Select("SELECT * FROM listing WHERE MATCH(title) AGAINST (#{searchTerm} in boolean mode) LIMIT #{limit} OFFSET #{offset}")
	List<Listing> searchListingsText(@Param("limit") int limit, @Param("offset") int offset, @Param("searchTerm") String searchTerm);
	
	@Select("<script> select  title, location, state, price, transmission, titleStatus, odometer, year, url, make, model, postedDate"
			+ " from valid_listing inner join listing on valid_listing.listingID = listing.listingID inner join location on listing.location = location.siteName"
			+ " where make=#{make}"
			+ "<if test=\"model != null\"> AND model =#{model}</if>"
			+ "<if test=\"state != null\"> AND state =#{state}</if>"
			+ "<if test=\"minYear > 0\"> AND year &gt;= #{minYear}</if>"
			+ "<if test=\"maxYear > 0\"> AND year &lt;= #{maxYear}</if>"
			+ "<if test=\"minOdometer > 0\"> AND odometer &gt;= #{minOdometer}</if>"
			+ "<if test=\"maxOdometer > 0\"> AND odometer &lt;= #{maxOdometer}</if>"
			+ "<if test=\"minPrice > 0\"> AND price &gt;= #{minPrice}</if>"
			+ "<if test=\"maxPrice > 0\"> AND price &lt;= #{maxPrice}</if>"
			+ "<if test=\"transmission != null\"> AND transmission =#{transmission}</if>"
			+ "<if test=\"titleStatus != null\"> AND titleStatus =#{titleStatus}</if>"
			+ "LIMIT #{limit} OFFSET #{offset}"
			+ "</script>"
			)
	List<ValidListingSearchResult> searchValidListings(ValidListingSearchRequest searchRequest);
	
}
