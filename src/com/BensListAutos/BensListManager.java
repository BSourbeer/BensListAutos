package com.BensListAutos;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import com.BensListAutos.DAO.ListingDAO;
import com.BensListAutos.DAO.LocationDAO;
import com.BensListAutos.DAO.MakeModelDAO;
import com.BensListAutos.DAO.ValidListingDAO;
import com.BensListAutos.util.BensListConstants;
import com.BensListAutos.vo.Listing;
import com.BensListAutos.vo.Location;
import com.BensListAutos.vo.ValidCar;
import com.BensListAutos.vo.ValidListing;
import com.BensListAutos.vo.ValidListingSearchRequest;
import com.BensListAutos.vo.ValidListingSearchResult;


public class BensListManager {
	
	private static Logger logger = LogManager.getLogger("BensListManager");
	
	@Autowired
	ListingDAO listingDAO;
	@Autowired
	ValidListingDAO validListingDAO;
	@Autowired
	MakeModelDAO makeModelDAO;
	@Autowired
	LocationDAO locationDAO;
	
	/*
	 *In this method, we are going to run through our existing data and try to validate it.
	 *When the data is validated, it is inserted into the valid_listings table, which has a FK
	 *to the listings table for listingID.  
	 *The query that we are using to pull existing data from our listings table will select only
	 *listings that we have not yet validated, to avoid trying the same data more than once.  
	 *
	 *validateStatus:
	 *'I' = initial state.  Not yet checked
	 *'V' = validated
	 *'F' = failed to validate
	 *
	 */
	public void validateListings(String selectValidateStatus){
		boolean emptyFlag = false;
		int limit = 10;
		int offset = 0;
		int offsetStep = 10;
		if (StringUtils.equals(selectValidateStatus, BensListConstants.VALIDATE_STATUS_INITIAL)){
			offsetStep = 0;
		}
		int valdiateStatusCounter = 0;
		ValidCar yearMakeModelCar = new ValidCar();
		ValidCar titleCar = new ValidCar();
		ValidListing validListing = new ValidListing();
		List<Listing> listings = new ArrayList<Listing>();
		//  1 - select 10 records that have a validateStatus of 'I'
		//  2 - go through the yearMakeModel field, try to get a match on the year, make and model
		//  3 - go through the title field and try to get a match on year, make and model using same method
		//			lets call it ValidCar matchYearMakeModel(String s)
		//  4 - if we have a complete valid car, insert it into the valid_listings table
		while (!emptyFlag){
			logger.info("Validating offset: " + offset);
			listings = listingDAO.selectPagedListings(limit, offset, selectValidateStatus);
			if (listings.isEmpty()){
				emptyFlag = true;
			}
			else {
				for (int i=0; i<listings.size(); i++){
					
					yearMakeModelCar = matchYearMakeModel(listings.get(i).getYearMakeModel());
					if (isValidCar(yearMakeModelCar)){
						//insert the yearMakeModelCar into db
						
						validListing.setListingID(listings.get(i).getListingID());
						validListing.setMake(yearMakeModelCar.getMake());
						validListing.setModel(yearMakeModelCar.getModel());
						validListing.setYear(yearMakeModelCar.getYear());
						validListingDAO.insertValidListing(validListing);
						
						//update the listing to 'V'
						listings.get(i).setValidateStatus(BensListConstants.VALIDATE_STATUS_VALIDATED);
						valdiateStatusCounter++;
						listingDAO.updateValidateStatus(listings.get(i));
					}
					else {
						titleCar = matchYearMakeModel(listings.get(i).getTitle());
						if (isValidCar(titleCar)){
							//insert the titleCar into db
							validListing.setListingID(listings.get(i).getListingID());
							validListing.setMake(titleCar.getMake());
							validListing.setModel(titleCar.getModel());
							validListing.setYear(titleCar.getYear());
							validListingDAO.insertValidListing(validListing);
							
							//update the listing to 'V'
							listings.get(i).setValidateStatus(BensListConstants.VALIDATE_STATUS_VALIDATED);
							valdiateStatusCounter++;
							listingDAO.updateValidateStatus(listings.get(i));
						}
						else{
							//update the listing to 'F'
							listings.get(i).setValidateStatus(BensListConstants.VALIDATE_STATUS_FAILED);
							valdiateStatusCounter++;
							listingDAO.updateValidateStatus(listings.get(i));
						}
					}
				}
			}
			offset+=offsetStep;
		}
		logger.info("Finished validating " + valdiateStatusCounter);
	}
	
	private ValidCar matchYearMakeModel(String s){
		
		ValidCar validCar = new ValidCar();
		boolean matched = false;
		
		// - if this string is empty, just return
		if (StringUtils.isEmpty(s)){
			return validCar;
		}
		// put each space separated word of this title into wordList
		List<String> wordList = new ArrayList<String>();
		wordList = Arrays.asList(s.split(" "));
		
		// - go through each word and try to match it to a year, make, or a model.  Once matched, remove it from the arrayList
		for (int i=0; i<wordList.size(); i++){
			matched = false;
				
			// - YEAR
			if (validCar.getYear() == 0 && StringUtils.isNumeric(wordList.get(i))){
				// - this is a candidate to be the year, try to validate
				int validatedYear = validateYear(wordList.get(i));
				if (validatedYear != 0){
					validCar.setYear(validatedYear);
					// - set this flag so that we don't try to match make/model of this word.
					matched = true;
				}
			}
			
			// - MAKE
			if (!matched && StringUtils.isEmpty(validCar.getMake())){
				String validatedMake = validateMake(wordList.get(i));
					
				if (StringUtils.isNotBlank(validatedMake)){
					validCar.setMake(validatedMake);
					matched = true;
				}
			}
			
			// - MODEL
			// - we only check the model after we get a make matched, this is to improve our query to match a model.
			if (!matched && !StringUtils.isEmpty(validCar.getMake()) && StringUtils.isEmpty(validCar.getModel())){
				String validatedModel = validateModel(wordList.get(i), validCar.getMake());
					
				if (StringUtils.isNotBlank(validatedModel)){
					validCar.setModel(validatedModel);
				}
			}
		}
		return validCar;
	}
	
	private int validateYear(String possibleYearStr){
		int yearInt = 0;
		//see if we can parse this into an integer
		try {
			yearInt = Integer.parseInt(possibleYearStr);
		}catch(Exception e){
			return 0;
		}
		
		//check the range of this number to make sure it is a valid possibility.
		if (yearInt >=0 && yearInt <= 16){
			yearInt += 2000;
		}
		else if (yearInt >= 18 && yearInt <= 99){
			yearInt += 1900;
		} 
		else if (yearInt < 1900 || yearInt > 2016){
			//this is an invalid year
			return 0;
		}
		
		return yearInt;
	}
	
	private String validateMake(String possibleMake){
		possibleMake = slangCheck(possibleMake);
		
		if (makeModelDAO.findMake(possibleMake)){
			return possibleMake.toUpperCase();
		}
		else {
			return "";
		}
	}
	
	private String validateModel(String possibleModel, String validatedMake){
		
		if (makeModelDAO.findMakeModel(validatedMake, possibleModel)){
			return possibleModel.toUpperCase();
		}
		else {
			return "";
		}
	}
	
	private boolean isValidCar(ValidCar validCar){
		if (validCar.getYear() != 0 && StringUtils.isNotBlank(validCar.getMake()) && StringUtils.isNotBlank(validCar.getModel())){
			return true;
		}
		else return false;
	}
	
	private String slangCheck(String s){
		if (StringUtils.equalsIgnoreCase("chevy", s) || StringUtils.equalsIgnoreCase("chev", s)){
			return "Chevrolet";
		} else if (StringUtils.equalsIgnoreCase("suby", s)){
			return "Subaru";
		} else if ( StringUtils.equalsIgnoreCase("vw", s)){
			return "Volkswagen";
		}  else if ( StringUtils.equalsIgnoreCase("infinity", s)){
			return "Infiniti";
		}  else if ( StringUtils.equalsIgnoreCase("datsun", s)){
			return "Nissan";
		}
		
		return s;
		
	}
	
	public String scanSites(){
		
		String result = BensListConstants.RESULT_SUCCESS;
		String searchPath = BensListConstants.CL_SEARCH_PATH_CTO;
		// - retrieve our list of locations from the db
		List<Location> locations = locationDAO.selectAllLocations();
		
		//this needs to be done for each page...for each site
		for (int i =0; i<locations.size(); i++){
			for (int j = 0; j<BensListConstants.CL_SEARCH_MAX_RESULTS; j+=BensListConstants.CL_SEARCH_RESULTS_PER_PAGE){
				//visit each link on this page
				logger.info("Scanning site: " + locations.get(i).getSiteName() + " Page Offset:" + j);
				scanPage(locations.get(i).getSiteName(),searchPath + BensListConstants.CL_SEARCH_PATH_PAGE_PARAM + j);
			}
		}
		return result;
	}
	
	private void scanPage(String location, String searchPath){
		String baseSite = "http://" + location + ".craigslist.org";
		try{
			Document doc = Jsoup.connect(baseSite+searchPath).get();
			Elements adList = doc.select(".hdrlnk");
			
			
			//  we need to append this href link to the name of the current craigslist site
			//  ex: http://philadelphia.craigslist.org{link}
			Document currentAd = null;
			for (Element currentLink: adList){
				currentAd = Jsoup.connect(baseSite + currentLink.attr("href")).get();
				
				Listing listing = new Listing();
				listing.setLocation(location);
				// set the url while we have it handy
				listing.setUrl(baseSite + currentLink.attr("href"));
				
				//--------------------Get Attributes listed on the right side of the page.------------------------------------
				
				Elements attributes = currentAd.select(".attrgroup");
				for (Element currentAttr :attributes){
					Elements spanTags = currentAttr.getElementsByTag("span");
					//need to iterate through all of the span tags, parse each until hitting the : delimiter and matching that first
					//portion to a list of valid categories, then assign the rest of the string to a vo.
					
					for (Element currentSpan : spanTags){
						String spanText = currentSpan.text();
						
						String splitString[] = spanText.split(":", 2);
						if (splitString.length == 1){
							// this is a makeModelYear attribute
							if (!splitString[0].trim().equals("more ads by this user")){
								listing.setYearMakeModel(splitString[0].trim());
							}
						} 
						else if (splitString.length > 1){
							// this is an flexibleAttribute...need to figure out what we have so we can set it on the vo
							String category = splitString[0].trim();
							String value = splitString[1].trim();
							try{
								if (category.equals("odometer")){
									listing.setOdometer(Integer.parseInt(value));
								}
								else if (category.equals("condition")){
									listing.setCondition(value);
								}
								else if (category.equals("cylinders")){
									listing.setCylinders(value);
								}
								else if (category.equals("drive")){
									listing.setDrive(value);
								}
								else if (category.equals("fuel")){
									listing.setFuel(value);
								}
								else if (category.equals("paint color")){
									listing.setColor(value);
								}
								else if (category.equals("title status")){
									listing.setTitleStatus(value);
								}
								else if (category.equals("size")){
									listing.setSize(value);
								}
								else if (category.equals("transmission")){
									listing.setTransmission(value);
								}
								else if (category.equals("type")){
									listing.setType(value);
								}
							} catch (Exception e){
								logger.error(e);
							}
						}
					}	
				}
				//-------------------------------------------------------------------------------------------------------------
				//-----------------------------------Get the title-------------------------------------------------------------
				Elements titleTextOnly = currentAd.select("#titletextonly");
				if (titleTextOnly.size()>0){
					String title = titleTextOnly.first().text();
					listing.setTitle(title);
				}
				//-------------------------------------------------------------------------------------------------------------
				//-----------------------------------Get the price-------------------------------------------------------------
				Elements priceClass = currentAd.select(".price");
				if (priceClass.size()>0){
					String priceStr = priceClass.first().text();
					priceStr = priceStr.replace("$","");
					listing.setPrice(Integer.parseInt(priceStr));
				}
				//-------------------------------------------------------------------------------------------------------------
				//-----------------------------------Get the description-------------------------------------------------------
				Elements postingBody = currentAd.select("#postingbody");
				if (postingBody.size()>0){
					String description = postingBody.first().text();
					listing.setDescription(description);
				}
				//-------------------------------------------------------------------------------------------------------------
				//-----------------------------------Get the posted date-------------------------------------------------------
				Elements timeAgo = currentAd.select(".timeago");
				if (timeAgo.size()>0){
					String datePostedStr = timeAgo.first().text();
					Date datePosted = Date.valueOf(datePostedStr.substring(0, 10));;
					listing.setPostedDate(datePosted);
				}
				//-------------------------------------------------------------------------------------------------------------
				try{
					// - insert into database
					listing.setValidateStatus(BensListConstants.VALIDATE_STATUS_INITIAL);
					listingDAO.insertListing(listing);
				}catch(Exception e){
					logger.error(e);
				}
				
			}
			
			
		}catch (IOException io){
			logger.error(io);
		}
	}
	
	public List<Listing> searchListingsText(int limit, int offset, String searchTerm){
		if (StringUtils.isNotBlank(searchTerm)){
			return listingDAO.searchListingsText(limit, offset, searchTerm);
		} else {
			return null;
		}
	}
	
	public List<ValidListingSearchResult> searchValidListings(ValidListingSearchRequest searchRequest){
		return listingDAO.searchValidListings(searchRequest);
	}
	
	public List<ValidCar> findModels(String make){
		if (StringUtils.isNotBlank(make)){
			return makeModelDAO.findModels(make);
		} else {
			return null;
		}
	}
	
	public List<ValidCar> findAllMakes(){
		return makeModelDAO.findAllMakes();
	}
}
