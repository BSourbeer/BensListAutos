<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.6.0/pure-min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script>
$(document).ready(function(){

});

$(function() {
	$("#scan").click(function() {
		$.ajax({
		    type: "GET",
			url: "http://localhost:8080/BensListAutos/scanSites",
			dataType: "html",
			success: function()
				{
					alert("success");
				}
		})
	});
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

<a class="pure-button pure-button-primary" style = "margin-left: 20px; margin-top: 20px;" id = "scan">Scan Craigslist Sites</a><br><br>
<a class="pure-button pure-button-primary" style = "margin-left: 20px; margin-top: 20px;" id = "validateInitial">Validate Initial Listings</a>
<a class="pure-button pure-button-primary" style = "margin-left: 20px; margin-top: 20px;" id = "validateFailed">Validate Failed Listings</a><br>

<div class="pure-controls">
        <style scoped>
			.button-admin {
		    color: white;
		    border-radius: 4px;
		    text-shadow: 0 1px 1px rgba(0, 0, 0, 0.2);
		    background: rgb(28, 184, 65);
		    font-size: 80%;
		    margin-left: 20px; 
		    margin-top: 100px;
		}
		</style>
		<a class="button-admin pure-button" href="/BensListAutos/">Back To Search Page</a>
</div>
</body>
</html>
