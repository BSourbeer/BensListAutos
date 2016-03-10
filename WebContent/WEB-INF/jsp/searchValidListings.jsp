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

});
</script>
<title>Search Result Page</title>
<style>
table {
    border-collapse: collapse;
    width: 100%;
}

table, td, th {
    border: 1px solid black;
}
</style>
</head>
<body>

<c:if test="${not empty searchResults}">
    <table>
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
    </table>
</c:if><br>

<a href=<c:url 
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
<a href=<c:url 
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
		</c:url>>previous page</a><br>
<a href="/BensListAutos/">new search</a>

</body>
</html>
