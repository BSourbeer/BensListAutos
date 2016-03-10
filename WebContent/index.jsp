<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
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

<form method = "get" action="/BensListAutos/searchListings">
	Search Text:<br>
	<input type="text" name ="searchTerm"><br><br>
	Results per page:<br>
	<select name = "limit">
		<option value="10">10</option>
		<option value="25">25</option>
		<option value="50">50</option>
		<option value="100">100</option>
	</select>
	<input type="submit" value="Search All Listings">
</form><br><br><br>



<form method = "get" action="/BensListAutos/searchValidListings">
	Make:<br>
	<select id = "makeSelect" name = "make">
		<option value="select">Select a Make</option>
	</select><br>
	Model:<br>
	<select id = "modelSelect" name = "model">
	</select><br>
	Minimum Year:<br>
	<select id = "minYearSelect" name = "minYear">
		<option value="">Optional</option>
	</select><br>
	Maximum Year:<br>
	<select id = "maxYearSelect" name = "maxYear">
		<option value="">Optional</option>
	</select><br>
	Minimum Odometer:<br>
	<input type="text" name ="minOdometer" placeholder = "Optional"><br>
	Maximum Odometer:<br>
	<input type="text" name ="maxOdometer" placeholder = "Optional"><br>
	Minimum Price:<br>
	<input type="text" name ="minPrice" placeholder = "Optional"><br>
	Maximum Price:<br>
	<input type="text" name ="maxPrice" placeholder = "Optional"><br>
	Transmission:<br>
	<select id = transmission name = "transmission">
		<option value="all">All</option>
		<option value="automatic">Automatic</option>
		<option value="manual">Manual</option>
	</select><br>
	Title Status:<br>
	<select id = titleStatus name = "titleStatus">
		<option value="all">All</option>
		<option value="clean">Clean</option>
		<option value="lien">Lien</option>
		<option value="salvage">Salvage</option>
		<option value="rebuilt">Rebuilt</option>
		<option value="missing">Missing</option>
		<option value="parts only">Parts Only</option>
	</select><br>
	State:<br>
	<select id = state name = "state">
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
	</select><br>
	<input type="checkbox" name="average" value="true">Calculate Average Price of Results<br>
	
	<br>Results per page:<br>
	<select name = "limit">
		<option value="10">10</option>
		<option value="25">25</option>
		<option value="50">50</option>
		<option value="100">100</option>
	</select>
	<input type="submit" value="Search Valid Listings">
</form>

<br><a href="/BensListAutos/admin.jsp">ADMIN</a>


</body>
</html>
