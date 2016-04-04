<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="asiakas.Ostoskori" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- HUOM laitoin tämän tänne GitHubiin vaan jemmaan myöhempää testailua varten t:Pasi -->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OSTOSKORIN TESTAILUA</title>
</head>
<body>

	<table>
		<tr>
			<td><h2>Testisivu</h2></td>
		</tr>
		
		<tr>
			<td>
				<form action="ostoskori" method="get">
				 	<input type="number" name="item_id" placeholder="Item ID" />
				 	<input type="number" name="item_quantity" placeholder="Quantity" />
					<input type="hidden" name="added" value="true">
					<button type="submit" name="lisaa">Add item</button>
					<button type="submit" name="hae">Fetch stuff</button>
				</form>
			</td>
		</tr>
		<tr>
			<td><a href="#">Remove item ability does not work yet</a></td>
		</tr>
	</table>

</body>
</html>