package com.BensListAutos.DAO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.BensListAutos.mapper.MakeModelMapper;
import com.BensListAutos.util.MyBatisUtil;
import com.BensListAutos.vo.ValidCar;

public class MakeModelDAO {
	
	public ValidCar selectData(String make, String model){
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		MakeModelMapper mapper = session.getMapper(MakeModelMapper.class);
		
		ValidCar validCar = mapper.selectTest(make, model);
		session.close();
		return validCar;
	}
	
	public boolean findMake(String make){
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		MakeModelMapper mapper = session.getMapper(MakeModelMapper.class);
		
		boolean isValidMake = mapper.findMake(make);
		session.close();
		return isValidMake;
	}
	
	public boolean findMakeModel(String make, String model){
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		MakeModelMapper mapper = session.getMapper(MakeModelMapper.class);
		
		boolean isValidMakeModel = mapper.findMakeModel(make, model);
		session.close();
		return isValidMakeModel;
	}
	
	public List<ValidCar> findModels(String make){
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		MakeModelMapper mapper = session.getMapper(MakeModelMapper.class);
		
		List<ValidCar> modelList= mapper.findModels(make);
		session.close();
		return modelList;
	}
	
	public List<ValidCar> findAllMakes(){
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		MakeModelMapper mapper = session.getMapper(MakeModelMapper.class);
		
		List<ValidCar> makeList= mapper.findAllMakes();
		session.close();
		return makeList;
	}
}
