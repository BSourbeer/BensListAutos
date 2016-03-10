#BensListAutos

## Synopsis

This is a tool to scan craigslist automotive "for sale" listings and store the data in a local database.  The information from the ads can then be validated and matched to a valid year, make and model so that we can more easily query the data.  This strategy results in an improved filtering of craigslist ads and centralizes the data from various local craigslist sites into a single Mysql database.  The only negative of this strategy is that craigslist has implemented a limit of requests per IP, so the scraping of live ads is currently only functional for 5-10k ads per day per IP.

## How it works

#####Scraping data#####

The first step is to scan all current craigslist locations, parse the automotive ads ("for sale - by owner"), and insert into our listing table.  The html parsing is handled by jsoup.
```java
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
```

Here is a quick snippet of how jsoup is being used to parse the html and retrieve the data that we want to store.
```java
        //-----------------------------------Get the title-------------------------------------------------------------
				Elements titleTextOnly = currentAd.select("#titletextonly");
				if (titleTextOnly.size()>0){
					String title = titleTextOnly.first().text();
					listing.setTitle(title);
				}
        //-------------------------------------------------------------------------------------------------------------
```

After we parse all of the data that we need, we insert into the listing table.  The application is using MyBatis annotations to insert the Listing object into the mysql database.
```java
@Insert("INSERT into Listing(title, url, location, yearMakeModel, odometer, vehicleCondition, cylinders, drive, fuel, color, titleStatus, size, transmission, vehicleType, description, price, postedDate, validateStatus) "
			+ "VALUES(#{title}, #{url}, #{location}, #{yearMakeModel}, #{odometer}, #{condition}, #{cylinders}, #{drive}, #{fuel}, #{color}, #{titleStatus}, #{size}, #{transmission}, #{type}, #{description}, #{price}, #{postedDate}, #{validateStatus})")
	void insertListing (Listing listing);
```

#####Data Validation#####

Now that the craigslist data is stored locally, it is now time to attempt to match each listing into a year, make, and model.  We have a table of valid makes and models, which is what we are matching against.  A craigslist ad has two fields of interest in this portion, a *title*, and a field that is intended to display year, make, and model, which we will call *yearMakeModel*.  Unfortunately, the make and model portion of *yearMakeModel* is entered by the seller without validation, so it often is contains garbage information.

Regardless, our strategy is to attempt to parse out a valid year, make, and model from the *title* and *yearMakeModel* fields.  

```java
	private ValidCar matchYearMakeModel(String s){
		
		ValidCar validCar = new ValidCar();
		// - if this string is empty, just return
		if (StringUtils.isEmpty(s)){
			return validCar;
		}
		// put each space separated word of this title into wordList
		List<String> wordList = new ArrayList<String>();
		wordList = Arrays.asList(s.split(" "));
		
		// - go through each word and try to match it to a year, make, or a model.  Once matched, remove it from the arrayList
		for (int i=0; i<wordList.size(); i++){
			boolean matched = false;
				
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
```

If we are able to put together a valid year, make, and model, then we insert into the Valid_Listing table.  

#####Searching#####

Currently, there is a basic front end that allows the user to search by text or search by selecting/entering filters to search the validated information.  The text search will search all of the listings in the listing table, even those that have failed validation.  The **ADMIN** link at the bottom leads to a page that allows the user to launch the scanning of craigslist ads and validate existing data.

![Search Page](http://i.imgur.com/aXsqboM.png)

If we were to submit a search for 
 - Make: Subaru
 - Model: WRX
 - Transmission: manual
 - 
![Results Page](http://i.imgur.com/8dkiIgN.png)


## Motivation

Craigslist currently has no way of searching on a national basis and does not provide any sort of API to access their large pool of data.  Many people search for used cars on a larger radius than what is supplied by craigslist, so their only option is to visit multiple craigslist sites and search them individually.  The goal with this project was to solve that solution while simultaneously provide the ability to filer search results by year, make, and model.

In addition to the search functionality, my goals are to be able to use this data to provide meaningful data to assist in researching pricing trends of used cars.  If we were able to continue to track this data over time, we would be able to do the following:

 - Discover pricing differences on a state/regional basis
 - View pricing trends at certain times of year.  For example, is it really cheaper to buy a convertable in the winter?
 - Confirm the accuracy of Kelly Blue Book data
 - Determine which vehicles are currently appreciating or depreciating in value
 - *etc.*


## Installation

To run this project locally, you must:
 - execute the sql located in /BensListAutos/sql/
 - edit BensListAutos/src/com/BensListAutos/config/mybatis-config.xml to point at your local mysql database.
 - deploy the project to your local tomcat 8 instance
