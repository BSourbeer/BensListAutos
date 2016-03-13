package com.BensListAutos.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.BensListAutos.BensListManager;
import com.BensListAutos.util.BensListConstants;
import com.BensListAutos.vo.Listing;
import com.BensListAutos.vo.ValidCar;
import com.BensListAutos.vo.ValidListingSearchRequest;
import com.BensListAutos.vo.ValidListingSearchResult;

@Controller
@EnableWebMvc
public class BensListController {
	
	private static Logger logger = LogManager.getLogger("BensListController");
	
	@Autowired
	BensListManager bensListManager;
	
	@RequestMapping(path = "/scanSites", method = RequestMethod.GET)
	@ResponseBody
	public String scanSites(){
		bensListManager.scanSites();
		return "Finished Scanning";
	}
	
	@RequestMapping(path = "/validateListings", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String validateListings(@RequestParam(value="selectValidateStatus", defaultValue=BensListConstants.VALIDATE_STATUS_INITIAL) String selectValidateStatus){
		logger.info("SelectValidateStatus: " + selectValidateStatus);
		bensListManager.validateListings(selectValidateStatus);
		return "Successful Validation";
	}
	
	@RequestMapping(value = "/searchListings")
	public ModelAndView searchListings(@RequestParam(value="limit") int limit,
										@RequestParam(value="offset", defaultValue="0") int offset,
										@RequestParam(value="searchTerm") String searchTerm){
		ModelAndView mv = new ModelAndView("/searchListings");
		List<Listing> listings= new ArrayList<Listing>();
		listings = bensListManager.searchListingsText(limit, offset, searchTerm);
		mv.addObject("listings",listings);
		mv.addObject("limit", limit);
		mv.addObject("offset", offset+limit);
		
		if (offset>=limit){
			mv.addObject("previousOffset", offset-limit);
		} else {
			mv.addObject("previousOffset", 0);
		}
		
		mv.addObject("searchTerm", searchTerm);
		
		logger.info("Limit: " + limit + " | Offset: " + offset + " | searchTerm: " + searchTerm);
		return mv;
	}
	
	@RequestMapping(value = "/searchValidListings")
	public ModelAndView searchValidListings(@RequestParam(value="limit") int limit,
										@RequestParam(value="offset", defaultValue="0") int offset,
										@RequestParam(value="make") String make,
										@RequestParam(value="model") String model,
										@RequestParam(value="minYear") String minYear,
										@RequestParam(value="maxYear") String maxYear,
										@RequestParam(value="minOdometer") String minOdometer,
										@RequestParam(value="maxOdometer") String maxOdometer,
										@RequestParam(value="minPrice") String minPrice,
										@RequestParam(value="maxPrice") String maxPrice,
										@RequestParam(value="transmission") String transmission,
										@RequestParam(value="titleStatus") String titleStatus,
										@RequestParam(value="state") String state){
		
		ModelAndView mv = new ModelAndView("/searchValidListings");
		//check to make sure integers are valid and set them on the vo
		ValidListingSearchRequest searchRequest = new ValidListingSearchRequest();
		searchRequest.setMake(make);
		searchRequest.setLimit(limit);
		searchRequest.setOffset(offset);
		try{
			if (StringUtils.isNotEmpty(model) && !StringUtils.equals(model, "all")){
				searchRequest.setModel(model);
			}
			if (StringUtils.isNotEmpty(minYear)){
				searchRequest.setMinYear(Integer.parseInt(minYear));
			}
			if (StringUtils.isNotEmpty(maxYear)){
				searchRequest.setMaxYear(Integer.parseInt(maxYear));
			}
			if (StringUtils.isNotEmpty(minOdometer)){
				searchRequest.setMinOdometer(Integer.parseInt(minOdometer));
			}
			if (StringUtils.isNotEmpty(maxOdometer)){
				searchRequest.setMaxOdometer(Integer.parseInt(maxOdometer));
			}
			if (StringUtils.isNotEmpty(minPrice)){
				searchRequest.setMinPrice(Integer.parseInt(minPrice));
			}
			if (StringUtils.isNotEmpty(maxPrice)){
				searchRequest.setMaxPrice(Integer.parseInt(maxPrice));
			}
			if (StringUtils.isNotEmpty(transmission) && !StringUtils.equals(transmission, "all")){
				searchRequest.setTransmission(transmission);
			}
			if (StringUtils.isNotEmpty(titleStatus) && !StringUtils.equals(titleStatus, "all")){
				searchRequest.setTitleStatus(titleStatus);
			}
			if (StringUtils.isNotEmpty(state)  && !StringUtils.equals(state, "all")){
				searchRequest.setState(state);
			}
			
			List<ValidListingSearchResult> searchResults = bensListManager.searchValidListings(searchRequest);
			
			mv.addObject("searchResults",searchResults);
			
			mv.addObject("searchRequest",searchRequest);
			mv.addObject("limit", limit);
			mv.addObject("offset", offset+limit);
			if (offset>=limit){
				mv.addObject("previousOffset", offset-limit);
			} else {
				mv.addObject("previousOffset", 0);
			}
			
		}catch (Exception e){
			mv.addObject("error", "Data validation error");
		} 
		logger.info("Limit: " + searchRequest.getLimit() 
				+ " | Offset: " + searchRequest.getOffset()
				+ " | Make: " + searchRequest.getMake()
				+ " | Model: " + searchRequest.getModel()
				+ " | MaxOdometer: " + searchRequest.getMaxOdometer()
				+ " | MaxPrice: " + searchRequest.getMaxPrice()
				+ " | MaxYear: " + searchRequest.getMaxYear()
				+ " | MinOdometer: " + searchRequest.getMinOdometer()
				+ " | MinPrice: " + searchRequest.getMinPrice()
				+ " | MinYear: " + searchRequest.getMinYear()
				+ " | State: " + searchRequest.getState()
				+ " | TitleStatus: " + searchRequest.getTitleStatus()
				+ " | Transmission: " + searchRequest.getTransmission());
		
		return mv;
	}
	
	@RequestMapping(path = "/findModels", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<ValidCar> findModels(@RequestParam(value="make") String make){
		logger.info("Make: " + make);
		return bensListManager.findModels(make);
	}
	
	@RequestMapping(path = "/findAllMakes", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<ValidCar> findAllMakes(){
		return bensListManager.findAllMakes();
	}
}

