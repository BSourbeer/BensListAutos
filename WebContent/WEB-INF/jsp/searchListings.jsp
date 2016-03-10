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

<c:if test="${not empty listings}">
    <table>
		<tr>
			<th>Title</th>
			<th>YearMakeModel</th>
			<th>location</th>
			<th>url</th>
		</tr>
        <c:forEach var="l" items="${listings}">
            <tr>
                <td>${l.title}</td>
                <td>${l.yearMakeModel}</td>
                <td>${l.location}</td>   
                <td><a href="${l.url}">${l.url}</a></td>    
            </tr>
        </c:forEach>
    </table>
</c:if><br>

<a href=<c:url 
			value="/searchListings">
				<c:param name="limit" value="${limit}"/>
				<c:param name="offset" value="${offset}"/>
				<c:param name="searchTerm" value="${searchTerm}"/>
		</c:url>>next page</a><br>
<a href=<c:url 
			value="/searchListings">
				<c:param name="limit" value="${limit}"/>
				<c:param name="offset" value="${previousOffset}"/>
				<c:param name="searchTerm" value="${searchTerm}"/>
		</c:url>>previous page</a><br>
<a href="/BensListAutos/">new search</a>

</body>
</html>
