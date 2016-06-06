<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="fr.pizzeria.model.Pizza"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript">
	function deletePerf(id) {
		$.ajax({
			type : "DELETE",
			url : '<c:url value="/api/performance/delete/" />' + id,
			headers : {
				"${_csrf.headerName}" : "${_csrf.token}"
			},
			success : function() {
				$("#perf-" + id).remove();
			},
			error : function(msg) {
			}
		});
	}
	function deleteAllPerf() {
		$.ajax({
			type : "DELETE",
			url : '<c:url value="/api/performance/deleteall" />',
			headers : {
				"${_csrf.headerName}" : "${_csrf.token}"
			},
			success : function() {
				$('[id^="perf-"]').remove();
			},
			error : function(msg) {
			}
		});
	}
</script>
<title>Performances</title>
</head>
<body>
	<div class="container">
		<h1>Liste de performance</h1>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Id.</th>
					<th>Date</th>
					<th>Service</th>
					<th>Temps d'execution</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="performances" value="${list}" />
				<c:forEach var="p" items="${performances}">
					<tr id="perf-${p.id}">
						<td>${p.id}</td>
						<td><fmt:formatDate type="BOTH" value="${p.date}" /></td>
						<td>${p.service}</td>
						<td>${p.tempsExecution}ms</td>
						<td><a class="btn btn-danger" href="#" onclick="deletePerf('${p.id}')">Supprimer</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<a class="btn btn-danger" href="#" onclick="deleteAllPerf()">Vider la table</a>
	</div>
</body>
</html>