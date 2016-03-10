package com.BensListAutos.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.BensListAutos.vo.ValidCar;

public interface MakeModelMapper {
	@Results({
		@Result(property = "make", column = "make"),
		@Result(property = "model", column = "model")
	})
	
	@Select("SELECT make, model FROM make_model WHERE make = #{make} AND model = #{model}")
	ValidCar selectTest(@Param("make") String make, @Param("model") String model);
	
	@Select("SELECT IF(COUNT(*) = 0, 0, 1) FROM make_model WHERE make= #{make}")
	boolean findMake(@Param("make") String make);
	
	@Select("SELECT IF(COUNT(*) = 0, 0, 1) FROM make_model WHERE make= #{make} and model = #{model}")
	boolean findMakeModel(@Param("make") String make, @Param("model") String model);
	
	@Select("SELECT make, model FROM make_model WHERE make = #{make}")
	List<ValidCar> findModels(@Param("make") String make);
	
	@Select("SELECT distinct(make) FROM make_model")
	List<ValidCar> findAllMakes();
}
