package com.BensListAutos.DAO;

import org.apache.ibatis.session.SqlSession;

import com.BensListAutos.mapper.ValidListingMapper;
import com.BensListAutos.util.MyBatisUtil;
import com.BensListAutos.vo.ValidListing;

public class ValidListingDAO {
	
	public void insertValidListing(ValidListing validListing){
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		ValidListingMapper mapper = session.getMapper(ValidListingMapper.class);
		
		mapper.insertValidListing(validListing);
		session.commit();
		session.close();
	}
}
