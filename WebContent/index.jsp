<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/pure-min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script>
$(document).ready(function(){
	$.ajax({
	    type: "GET",
		url: "http://localhost:8080/BensListAutos/findAllMakes",
		dataType: "json"
	}).done(function(reply) {
		// Loop through JSON response
		$.each(reply, function(key, value) {   
			$('#makeSelect').append($('<option>', { value: value.make }).text(value.make)); 
		});
	});
});

$(function() {
	$("#makeSelect").change(function() {
        var makeName = $(this).val();
	
		$.ajax({
		    type: "GET",
			url: "http://localhost:8080/BensListAutos/findModels",
			data: { make: makeName },
			dataType: "json"
		}).done(function(reply) {
		    // Clear options
			$("#modelSelect").find("option").remove();
		    
			$('#modelSelect').append($('<option>', { value: "all" }).text("ALL MODELS")); 
			// Loop through JSON response
			$.each(reply, function(key, value) {   
				$('#modelSelect').append($('<option>', { value: value.model }).text(value.model)); 
			});
			
		});

	});
});

$(function(){
    var $select = $("#minYearSelect");
    for (i=2016;i>=1900;i--){
        $select.append($('<option></option>').val(i).html(i))
    }
});
$(function(){
    var $select = $("#maxYearSelect");
    for (i=2016;i>=1900;i--){
        $select.append($('<option></option>').val(i).html(i))
    }
});

	
</script>
<title>Search Page</title>
</head>
<body>

<br><form class = "pure-form pure-form-aligned" method = "get" action="/BensListAutos/searchListings">
	
	<div class="pure-control-group">
            <label for="searchTerm">Search Text</label>
            <input type="text" name ="searchTerm"></select>
    </div>
        
	<div class="pure-control-group">
            <label for="limit">Results Per Page</label>
            <select id = "limit" name = "limit">
				<option value="10">10</option>
				<option value="25">25</option>
				<option value="50">50</option>
				<option value="100">100</option>
			</select>
        </div>
	<div class="pure-controls">
            <button type="submit" class="pure-button pure-button-primary">Search All Listings</button>
    </div>
</form><br><br><br>

<form class="pure-form pure-form-aligned" method = "get" action="/BensListAutos/searchValidListings">
    <fieldset>
        <div class="pure-control-group">
            <label for="makeSelect">Make</label>
            <select id = "makeSelect" name = "make" required>
				<option value="">Select a Make</option>
			</select><br>
        </div>
        
        
        <div class="pure-control-group">
            <label for="modelSelect">Model</label>
            <select id = "modelSelect" name = "model" required></select>
        </div>
        
        
        <div class="pure-control-group">
            <label for="minYearSelect">Minimum Year</label>
            <select id = "minYearSelect" name = "minYear">
				<option value="">Optional</option>
			</select>
        </div>
        
        <div class="pure-control-group">
            <label for="maxYearSelect">Maximum Year</label>
            <select id = "maxYearSelect" name = "maxYear">
				<option value="">Optional</option>
			</select>
        </div>
        
        <div class="pure-control-group">
            <label for="minOdometer">Minimum Odometer</label>
            <input type="text" name ="minOdometer" id ="minOdometer" placeholder = "Optional">
        </div>
        
        <div class="pure-control-group">
            <label for="maxOdometer">Maximum Odometer</label>
            <input type="text" name ="maxOdometer" id ="maxOdometer" placeholder = "Optional">
        </div>
        
        <div class="pure-control-group">
            <label for="minPrice">Minimum Price</label>
            <input type="text" name ="minPrice" id = "minPrice" placeholder = "Optional">
        </div>
        
        <div class="pure-control-group">
            <label for="maxPrice">Maximum Price</label>
            <input type="text" name ="maxPrice" id = "maxPrice" placeholder = "Optional">
        </div>
        
        <div class="pure-control-group">
            <label for="transmission">Transmission</label>
            <select id = "transmission" name = "transmission">
				<option value="all">All</option>
				<option value="automatic">Automatic</option>
				<option value="manual">Manual</option>
			</select><br>
        </div>

        <div class="pure-control-group">
            <label for="titleStatus">Title Status</label>
            <select id = "titleStatus" name = "titleStatus">
				<option value="all">All</option>
				<option value="clean">Clean</option>
				<option value="lien">Lien</option>
				<option value="salvage">Salvage</option>
				<option value="rebuilt">Rebuilt</option>
				<option value="missing">Missing</option>
				<option value="parts only">Parts Only</option>
			</select><br>
        </div>

        <div class="pure-control-group">
            <label for="state">State</label>
            <select id = "state" name = "state">
				<option value="all">All</option>
				<option value="AL">Alabama</option>
				<option value="AK">Alaska</option>
				<option value="AZ">Arizona</option>
				<option value="AR">Arkansas</option>
				<option value="CA">California</option>
				<option value="CO">Colorado</option>
				<option value="CT">Connecticut</option>
				<option value="DC">Washington DC</option>
				<option value="DE">Delaware</option>
				<option value="FL">Florida</option>
				<option value="GA">Georgia</option>
				<option value="HI">Hawaii</option>
				<option value="ID">Idaho</option>
				<option value="IL">Illinois</option>
				<option value="IN">Indiana</option>
				<option value="IA">Iowa</option>
				<option value="KS">Kansas</option>
				<option value="KY">Kentucky</option>
				<option value="LA">Louisiana</option>
				<option value="ME">Maine</option>
				<option value="MD">Maryland</option>
				<option value="MA">Massachusetts</option>
				<option value="MI">Michigan</option>
				<option value="MN">Minnesota</option>
				<option value="MS">Mississippi</option>
				<option value="MO">Missouri</option>
				<option value="MT">Montana</option>
				<option value="NE">Nebraska</option>
				<option value="NV">Nevada</option>
				<option value="NH">New Hampshire</option>
				<option value="NJ">New Jersey</option>
				<option value="NM">New Mexico</option>
				<option value="NY">New York</option>
				<option value="NC">North Carolina</option>
				<option value="ND">North Dakota</option>
				<option value="OH">Ohio</option>
				<option value="OK">Oklahoma</option>
				<option value="OR">Oregon</option>
				<option value="PA">Pennsylvania</option>
				<option value="RI">Rhode Island</option>
				<option value="SC">South Carolina</option>
				<option value="SD">South Dakota</option>
				<option value="TN">Tennessee</option>
				<option value="TX">Texas</option>
				<option value="UT">Utah</option>
				<option value="VT">Vermont</option>
				<option value="VA">Virginia</option>
				<option value="WA">Washington</option>
				<option value="WV">West Virginia</option>
				<option value="WI">Wisconsin</option>
				<option value="WY">Wyoming</option>
			</select>
        </div>

        <div class="pure-control-group">
            <label for="limit">Results Per Page</label>
            <select id = "limit" name = "limit">
				<option value="10">10</option>
				<option value="25">25</option>
				<option value="50">50</option>
				<option value="100">100</option>
			</select>
        </div>

        <div class="pure-controls">
            <button type="submit" class="pure-button pure-button-primary">Search Validated Listings</button>
        </div>
        <br>
        <div class="pure-controls">
        	<style scoped>
		        .button-admin {
		            color: white;
		            border-radius: 4px;
		            text-shadow: 0 1px 1px rgba(0, 0, 0, 0.2);
		            background: rgb(223, 117, 20); /*orange */
		             font-size: 80%;
		        }
		    </style>
            <a class="button-admin pure-button" href="/BensListAutos/admin.jsp">Admin Page</a>
        </div>
    </fieldset>
</form>




</body>
</html>
