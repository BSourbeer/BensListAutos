package com.BensListAutos.DAO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.BensListAutos.mapper.ListingMapper;
import com.BensListAutos.util.MyBatisUtil;
import com.BensListAutos.vo.Listing;
import com.BensListAutos.vo.ValidListingSearchRequest;
import com.BensListAutos.vo.ValidListingSearchResult;

public class ListingDAO {
	
	public void insertListing(Listing listing){
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		ListingMapper mapper = session.getMapper(ListingMapper.class);
		
		try {
			mapper.insertListing(listing);
		} catch (Exception e){
			//this is to catch the duplicate key exception
			//TODO: logging
			System.out.println(e);
		}
		session.commit();
		session.close();
	}
	
	public List<Listing> selectPagedListings(int limit, int offset, String selectValidateStatus){
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		ListingMapper mapper = session.getMapper(ListingMapper.class);
		
		List<Listing> listings = mapper.selectPagedListings(limit, offset, selectValidateStatus);
		session.close();
		return listings;
	}
	
	public void updateValidateStatus(Listing listing){
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		ListingMapper mapper = session.getMapper(ListingMapper.class);
		
		mapper.updateValidateStatus(listing);
		session.commit();
		session.close();
	}
	
	public List<Listing> searchListingsText(int limit, int offset, String searchTerm){
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		ListingMapper mapper = session.getMapper(ListingMapper.class);
		
		List<Listing> listings = mapper.searchListingsText(limit, offset, searchTerm);
		session.close();
		return listings;
	}
	
	public List<ValidListingSearchResult> searchValidListings(ValidListingSearchRequest searchRequest){
		SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
		ListingMapper mapper = session.getMapper(ListingMapper.class);
		
		List<ValidListingSearchResult> searchResults = mapper.searchValidListings(searchRequest);
		session.close();
		return searchResults;
	}
	
	
}
