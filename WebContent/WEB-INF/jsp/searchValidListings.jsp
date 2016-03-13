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

});
</script>
<title>Search Result Page</title>
</head>
<body>

<c:if test="${not empty searchResults}">
    <table style = "width:100%" class="pure-table pure-table-bordered">
    	<thead>
			<tr>
				<th>Year</th>
				<th>Make</th>
				<th>Model</th>
				<th>Title</th>
				<th>Price</th>
				<th>Location</th>
				<th>State</th>
				<th>Odometer</th>
				<th>Title Status</th>
				<th>Transmission</th>
				<th>Posted Date</th>
				<th>URL</th>
			</tr>
		</thead>
		<tbody>
	        <c:forEach var="results" items="${searchResults}">
	            <tr>
	            	<td>${results.year}</td>
	            	<td>${results.make}</td>
	            	<td>${results.model}</td>
	                <td>${results.title}</td>
	                <td>${results.price}</td>
	                <td>${results.location}</td>
	                <td>${results.state}</td>
	                <td>${results.odometer}</td>
	                <td>${results.titleStatus}</td>
	                <td>${results.transmission}</td>
	                <td>${results.postedDate}</td>
	                <td><a href="${results.url}">${results.url}</a></td>    
	            </tr>
	        </c:forEach>
        </tbody>
    </table>
</c:if><br>

<a class="pure-button pure-button-primary" style = "margin-left: 20px;" href=<c:url 
			value="/searchValidListings">
				<c:param name="limit" value="${limit}"/>
				<c:param name="offset" value="${previousOffset}"/>
				<c:param name="make" value="${searchRequest.make}"/>
				<c:param name="model" value="${searchRequest.model}"/>
				<c:param name="minYear" value="${searchRequest.minYear}"/>
				<c:param name="maxYear" value="${searchRequest.maxYear}"/>
				<c:param name="minOdometer" value="${searchRequest.minOdometer}"/>
				<c:param name="maxOdometer" value="${searchRequest.maxOdometer}"/>
				<c:param name="minPrice" value="${searchRequest.minPrice}"/>
				<c:param name="maxPrice" value="${searchRequest.maxPrice}"/>
				<c:param name="transmission" value="${searchRequest.transmission}"/>
				<c:param name="titleStatus" value="${searchRequest.titleStatus}"/>
				<c:param name="state" value="${searchRequest.state}"/>
		</c:url>>previous page</a>

<a class="pure-button pure-button-primary" style = "margin-left: 10px;" href=<c:url 
			value="/searchValidListings">
				<c:param name="limit" value="${limit}"/>
				<c:param name="offset" value="${offset}"/>
				<c:param name="make" value="${searchRequest.make}"/>
				<c:param name="model" value="${searchRequest.model}"/>
				<c:param name="minYear" value="${searchRequest.minYear}"/>
				<c:param name="maxYear" value="${searchRequest.maxYear}"/>
				<c:param name="minOdometer" value="${searchRequest.minOdometer}"/>
				<c:param name="maxOdometer" value="${searchRequest.maxOdometer}"/>
				<c:param name="minPrice" value="${searchRequest.minPrice}"/>
				<c:param name="maxPrice" value="${searchRequest.maxPrice}"/>
				<c:param name="transmission" value="${searchRequest.transmission}"/>
				<c:param name="titleStatus" value="${searchRequest.titleStatus}"/>
				<c:param name="state" value="${searchRequest.state}"/>
		</c:url>>next page</a><br>

<div class="pure-controls">
        <style scoped>
			.button-admin {
		    color: white;
		    border-radius: 4px;
		    text-shadow: 0 1px 1px rgba(0, 0, 0, 0.2);
		    background: rgb(28, 184, 65);
		    font-size: 80%;
		    margin-left: 20px; 
		    margin-top: 30px;
		}
		</style>
		<a class="button-admin pure-button" href="/BensListAutos/">Back To Search Page</a>
</div>

</body>
</html>
