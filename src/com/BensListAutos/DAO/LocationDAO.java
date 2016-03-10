package com.BensListAutos.DAO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.BensListAutos.mapper.LocationMapper;
import com.BensListAutos.util.MyBatisUtil;
import com.BensListAutos.vo.Location;

public class LocationDAO {
	
	public List<Location> selectAllLocations(){
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		LocationMapper mapper = session.getMapper(LocationMapper.class);
		
		List<Location> locations = mapper.selectAllLocations();
		session.close();
		return locations;
	}
}
