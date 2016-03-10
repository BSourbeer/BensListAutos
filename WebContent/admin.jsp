<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script>
$(document).ready(function(){

});

$(function() {
	$("#validateInitial").click(function() {
		$.ajax({
		    type: "GET",
			url: "http://localhost:8080/BensListAutos/validateListings?selectValidateStatus=I",
			dataType: "html",
			success: function(data)
				{
					alert(data);
				}
		})
	});
});
$(function() {
	$("#validateFailed").click(function() {
		$.ajax({
		    type: "GET",
			url: "http://localhost:8080/BensListAutos/validateListings?selectValidateStatus=F",
			dataType: "html",
			success: function()
				{
					alert("success");
				}
		})
	});
});
</script>
<title>Admin Page</title>
</head>
<body>

<form method = "get" action="/BensListAutos/scanSites">
  <input type="submit" value="Scan craigslist sites">
</form><br>

<button type='button' id = "validateInitial">Validate Initial Listings</button><br>
<button type='button' id = "validateFailed">Validate Failed Listings</button><br>

<br><a href="/BensListAutos/">Back to search</a>
</body>
</html>
