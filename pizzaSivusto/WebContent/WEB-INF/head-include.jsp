<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link href="http://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<link type="text/css" rel="stylesheet" href="css/materialize.css"
	media="screen,projection" />
<link type="text/css" rel="stylesheet" href="css/style.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script type="text/javascript"
	src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="js/materialize.js"></script>
<c:if test="${not empty success }">
	<script type="text/javascript">
		Materialize.toast('${success}', 3000, 'teal lighten-3');
		Materialize.toast();
	</script>
</c:if>
<c:if test="${not empty virhe }">
	<script type="text/javascript">
		Materialize.toast('${virhe}', 3000, 'red lighten-2');
		Materialize.toast();
	</script>
</c:if>
<c:set var="pathi" val="/pizzaSivusto"></c:set>