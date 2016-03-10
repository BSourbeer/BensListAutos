package com.BensListAutos.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.BensListAutos.vo.Location;

public interface LocationMapper {
	
	@Results({
		@Result(property = "siteName", column = "siteName"),
		@Result(property = "city", column = "city"),
		@Result(property = "state", column = "state"),
		@Result(property = "lastScanDate", column = "lastScanDate")
	})
	
	@Select("SELECT * FROM Location where state = 'PA'")
	List<Location> selectAllLocations();
}
