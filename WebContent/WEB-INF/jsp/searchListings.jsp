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

<c:if test="${not empty listings}">
    <table style = "width:100%" class="pure-table pure-table-bordered">
	    <thead>
			<tr>
				<th>Title</th>
				<th>YearMakeModel</th>
				<th>location</th>
				<th>url</th>
			</tr>
		</thead>
		<tbody>
	        <c:forEach var="l" items="${listings}">
	            <tr>
	                <td>${l.title}</td>
	                <td>${l.yearMakeModel}</td>
	                <td>${l.location}</td>   
	                <td><a href="${l.url}">${l.url}</a></td>    
	            </tr>
	        </c:forEach>
        </tbody>
    </table>
</c:if><br>

<a class="pure-button pure-button-primary" style = "margin-left: 20px;" href=<c:url 
			value="/searchListings">
				<c:param name="limit" value="${limit}"/>
				<c:param name="offset" value="${previousOffset}"/>
				<c:param name="searchTerm" value="${searchTerm}"/>
		</c:url>>previous page</a>
<a class="pure-button pure-button-primary" style = "margin-left: 10px;" href=<c:url 
			value="/searchListings">
				<c:param name="limit" value="${limit}"/>
				<c:param name="offset" value="${offset}"/>
				<c:param name="searchTerm" value="${searchTerm}"/>
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
