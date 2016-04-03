<%@page import="bean.Kayttaja"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello E Fiori - Tervetuloa</title>
<jsp:include page="head-include.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="headertext">
			<h1>
				Tervetuloa takaisin,
				<c:out value="${kayttaja.etunimi } ${kayttaja.sukunimi }"></c:out>
				!
			</h1>
			<p class="flow-text">Vierailit palvelussamme viimeksi XX.XX.XX!</p>
			<a class="waves-effect waves-light btn-large"
			href="<c:url value='/pizza'/>"><i class="material-icons right">restaurant_menu</i>
			Menu</a> <br> <br>
	</div>

	<div class="row" id="main-content">
		<div class="row">
			<div class="col s12 m6 center-align">
				<h2>Diipa daapa</h2>
				Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
				<br><br>
				Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
			</div>
			<div class="col s12 m6 center-align">
				<h2>Tilaushistoria</h2>
				Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.
				<br><br>
				Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.
			</div>
		</div>
	</div>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>