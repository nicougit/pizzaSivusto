<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Aukioloajat</title>
<jsp:include page="head-include.jsp"></jsp:include>
<link href='https://fonts.googleapis.com/css?family=Martel:200'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Raleway:200italic' rel='stylesheet' type='text/css'>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>

<div class="row headertext">
				<h1>Muokkaa aukioloaikoja</h1>
			</div>
			
			
			
	<div class="row" id="main-content">
		 <div class="section col s12">	
		 		 
		  <form class="col s12" action="aukioloajat" method="post" id="aukioloajat">
		<p class="flow-text">Arkisin</p>
		 <div class="row">
		 <div class="input-field col s12 m5">
		  <select name="Arkisinavaaminen">
		   <option value="${aukiolo[0].aloitusaika}" selected><c:out value="${aukiolot[0].aloitusaika}"></c:out></option>
		  <c:forEach items="${selectit}" var="selecti">
		 <option>
		 <c:out value="${selecti}"></c:out>
		 </option>
		  </c:forEach>
		 </select>
		 <label for="Arkisinavaaminen" data-error="Virhe"></label>
		 </div>
		  <div class="col s12 m1"><p class="center">-</p></div>
		 <div class="input-field col s12 m5">
		  <select name="Arkisinsulkeminen">
		  <option value="${aukiolo[0].sulkemisaika}" selected><c:out value="${aukiolot[0].sulkemisaika}"></c:out></option>
		  <c:forEach items="${selectit}" var="selecti">
		 <option>
		 <c:out value="${selecti}"></c:out>
		 </option>
		  </c:forEach>
		 </select>
		 <label for="Arkisinsulkeminen" data-error="Virhe"></label>
		 </div>
		 </div>
		 
		 <p class="flow-text">Lauantaisin</p>
		 <div class="row">
		 <div class="input-field col s12 m5">
		  <select name="Lauantaisinavaaminen">
		   <option value="${aukiolo[0].aloitusaika}" selected><c:out value="${aukiolot[1].aloitusaika}"></c:out></option>
		  <c:forEach items="${selectit}" var="selecti">
		 <option>
		 <c:out value="${selecti}"></c:out>
		 </option>
		  </c:forEach>
		 </select>
		 <label for="Lauantaisinavaaminen" data-error="Virhe"></label>
		 </div>
		  <div class="col s12 m1"><p class="center">-</p></div>
		 <div class="input-field col s12 m5">
		  <select name="Lauantaisinsulkeminen">
		    <option value="${aukiolo[1].sulkemisaika}" selected><c:out value="${aukiolot[1].sulkemisaika}"></c:out></option>
		  <c:forEach items="${selectit}" var="selecti">
		 <option>
		 <c:out value="${selecti}"></c:out>
		 </option>
		  </c:forEach>
		 </select>
		 <label for="Lauantaisinsulkeminen" data-error="Virhe"></label>
		 </div>
		 </div>
		 
		 <p class="flow-text">Sunnuntaisin</p>
		 <div class="row">
		 <div class="input-field col s12 m5">
		  <select name="Sunnuntaisinavaaminen">
		   <option value="${aukiolo[0].aloitusaika}"selected><c:out value="${aukiolot[2].aloitusaika}"></c:out></option>
		  <c:forEach items="${selectit}" var="selecti">
		 <option>
		 <c:out value="${selecti}"></c:out>
		 </option>
		  </c:forEach>
		 </select>
		 <label for="Sunnuntaisinavaaminen" data-error="Virhe"></label>
		 </div>
		  <div class="col s12 m1"><p class="center">-</p></div>
		 <div class="input-field col s12 m5">
		  <select name="Sunnuntaisinsulkeminen">
		   <option value="${aukiolo[2].sulkemisaika}" selected><c:out value="${aukiolot[2].sulkemisaika}"></c:out></option>
		  <c:forEach items="${selectit}" var="selecti">
		 <option>
		 <c:out value="${selecti}"></c:out>
		 </option>
		  </c:forEach>
		 </select>
		 <label for="Sunnuntaisinsulkeminen" data-error="Virhe"></label>
		 </div>
		 
		 </div>
		  </form>
		 
		 <div class="col s12">
		 <button class="btn waves-effect waves-light btn-large" id="paivitaaukiolo" type="submit" form="aukioloajat">Päivitä aukioloajat</button>
		 </div>

		 </div>
		 </div>		 

</body>
</html>