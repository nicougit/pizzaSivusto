<%@page import="bean.Kayttaja"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="http://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<c:set var="url">${pageContext.request.contextPath}</c:set>
<link type="text/css" rel="stylesheet"
	href="${url }/css/materialize.css" />
<link type="text/css" rel="stylesheet" href="${url }/css/style.css" />
<link href='https://fonts.googleapis.com/css?family=Pacifico'
	rel='stylesheet' type='text/css'>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script type="text/javascript"
	src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="${url }/js/materialize.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".button-collapse").sideNav();
		$('.modal-trigger').leanModal();
		$('select').material_select();
	});
</script>
<c:if test="${not empty success }">
	<script type="text/javascript">
		$(document).ready(function() {
			Materialize.toast('${success}', 3000, 'teal lighten-3');
			Materialize.toast();
		});
	</script>
</c:if>
<c:if test="${not empty virhe }">
	<script type="text/javascript">
		$(document).ready(function() {
			Materialize.toast('${virhe}', 3000, 'red lighten-2');
			Materialize.toast();
		});
	</script>
</c:if>